package com.chatapp.service;

import com.chatapp.entity.Otp;
import com.chatapp.entity.User;
import com.chatapp.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${otp.length:6}")
    private int otpLength;

    @Value("${otp.expiry.minutes:5}")
    private int otpExpiryMinutes;

    public String generateOtp() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    public void saveOtp(User user, String otp) {
        // Delete expired OTPs for this user
        otpRepository.deleteByUserAndExpiresAtBefore(user, LocalDateTime.now());

        String otpHash = passwordEncoder.encode(otp);
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(otpExpiryMinutes);

        Otp otpEntity = new Otp(user, otpHash, expiresAt);
        otpRepository.save(otpEntity);
    }

    public boolean validateOtp(User user, String otp) {
        Optional<Otp> otpEntity = otpRepository.findLatestValidOtpForUser(user, LocalDateTime.now());

        if (otpEntity.isEmpty()) {
            return false;
        }

        Otp validOtp = otpEntity.get();

        // Check if OTP is expired
        if (validOtp.isExpired()) {
            return false;
        }

        // Check if OTP is already used
        if (validOtp.getUsed()) {
            return false;
        }

        // Compare OTP with stored hash
        boolean isValid = passwordEncoder.matches(otp, validOtp.getOtpHash());

        if (isValid) {
            // Mark OTP as used
            validOtp.setUsed(true);
            otpRepository.save(validOtp);
        }

        return isValid;
    }
}
