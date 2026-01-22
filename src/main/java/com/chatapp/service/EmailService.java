package com.chatapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String brevoApiKey;

    @Value("${brevo.api.url:https://api.brevo.com/v3}")
    private String brevoApiUrl;

    @Value("${brevo.sender.email:noreply@chatapp.com}")
    private String senderEmail;

    @Value("${brevo.sender.name:Chat App}")
    private String senderName;

    @Autowired
    private RestTemplate restTemplate;

    public void sendOtpEmail(String to, String otp) {
        try {
            String htmlContent = buildOtpEmailHtml(otp);
            sendTransactionalEmail(to, "Your Chat App OTP", htmlContent);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }

    /**
     * Send a transactional email via Brevo API
     */
    private void sendTransactionalEmail(String recipientEmail, String subject, String htmlContent) {
        try {
            // Build request body
            Map<String, Object> request = new HashMap<>();
            request.put("subject", subject);
            request.put("htmlContent", htmlContent);

            // Sender info
            Map<String, String> sender = new HashMap<>();
            sender.put("email", senderEmail);
            sender.put("name", senderName);
            request.put("sender", sender);

            // Recipients
            Map<String, Object> to = new HashMap<>();
            to.put("email", recipientEmail);
            List<Map<String, Object>> recipients = List.of(to);
            request.put("to", recipients);

            // Create headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", brevoApiKey);

            // Create HTTP entity
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

            // Send request to Brevo API
            String url = brevoApiUrl + "/smtp/send";
            restTemplate.postForObject(url, entity, String.class);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email via Brevo: " + e.getMessage(), e);
        }
    }

    private String buildOtpEmailHtml(String otp) {
        return "<html><body style=\"font-family: Arial, sans-serif;\">" +
                "<h2>Your Chat App OTP</h2>" +
                "<p>Your OTP is: <strong style=\"font-size: 24px; color: #007bff;\">" + otp + "</strong></p>" +
                "<p>This OTP is valid for 5 minutes.</p>" +
                "<p><strong>Do not share this OTP with anyone.</strong></p>" +
                "<p style=\"color: #666; font-size: 12px;\">If you didn't request this OTP, please ignore this email.</p>" +
                "</body></html>";
    }
}
