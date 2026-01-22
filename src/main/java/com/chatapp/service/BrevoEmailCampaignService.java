package com.chatapp.service;

import com.chatapp.dto.brevo.BrevoEmailCampaignRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

@Service
public class BrevoEmailCampaignService {

    @Value("${brevo.api.key}")
    private String brevoApiKey;

    @Value("${brevo.api.url:https://api.brevo.com/v3}")
    private String brevoApiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public BrevoEmailCampaignService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Send an email campaign via Brevo API
     *
     * @param campaignName      Name of the campaign
     * @param subject           Email subject
     * @param senderEmail       Sender email address
     * @param senderName        Sender name
     * @param htmlContent       HTML content of the email
     * @param recipientListIds  List of recipient list IDs
     * @param scheduledAt       Scheduled send time (ISO 8601 format, optional)
     * @return Response from Brevo API
     */
    public String sendEmailCampaign(String campaignName, String subject, String senderEmail,
                                   String senderName, String htmlContent, List<Long> recipientListIds,
                                   String scheduledAt) {
        try {
            // Build the request
            BrevoEmailCampaignRequest campaignRequest = new BrevoEmailCampaignRequest(
                    campaignName,
                    subject,
                    senderEmail,
                    senderName,
                    htmlContent,
                    recipientListIds,
                    scheduledAt
            );

            // Create headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", brevoApiKey);

            // Create HTTP entity
            HttpEntity<BrevoEmailCampaignRequest> entity = new HttpEntity<>(campaignRequest, headers);

            // Send request to Brevo API
            String url = brevoApiUrl + "/emailCampaigns";
            String response = restTemplate.postForObject(url, entity, String.class);

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email campaign via Brevo: " + e.getMessage(), e);
        }
    }
}
