package com.chatapp.repository;

import com.chatapp.entity.Otp;
import com.chatapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OtpRepository extends JpaRepository<Otp, UUID> {

    @Query("SELECT o FROM Otp o WHERE o.user = ?1 AND o.used = false AND o.expiresAt > ?2 ORDER BY o.createdAt DESC LIMIT 1")
    Optional<Otp> findLatestValidOtpForUser(User user, LocalDateTime now);

    void deleteByUserAndExpiresAtBefore(User user, LocalDateTime now);
}
