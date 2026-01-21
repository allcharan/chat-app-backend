package com.chatapp.dto;

import java.util.UUID;

public class AuthResponse {

    private UUID id;
    private String username;
    private String email;
    private Boolean isVerified;
    private String accessToken;

    public AuthResponse() {
    }

    public AuthResponse(UUID id, String username, String email, Boolean isVerified, String accessToken) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isVerified = isVerified;
        this.accessToken = accessToken;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
