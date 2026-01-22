package com.chatapp.controller;

import com.chatapp.service.BrevoEmailCampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/email-campaigns")
public class EmailCampaignController {

    @Autowired
    private BrevoEmailCampaignService brevoEmailCampaignService;

    /**
     * Send an email campaign via Brevo
     * Request body should contain:
     * - campaignName: Name of the campaign
     * - subject: Email subject
     * - senderEmail: Sender email address
     * - senderName: Sender name
     * - htmlContent: HTML content of the email
     * - recipientListIds: List of recipient list IDs from Brevo
     * - scheduledAt: (Optional) ISO 8601 scheduled time
     */
    @PostMapping("/send")
    public ResponseEntity<?> sendEmailCampaign(@RequestBody Map<String, Object> request) {
        try {
            String campaignName = (String) request.get("campaignName");
            String subject = (String) request.get("subject");
            String senderEmail = (String) request.get("senderEmail");
            String senderName = (String) request.get("senderName");
            String htmlContent = (String) request.get("htmlContent");
            @SuppressWarnings("unchecked")
            List<Long> recipientListIds = (List<Long>) request.get("recipientListIds");
            String scheduledAt = (String) request.get("scheduledAt");

            // Validate required fields
            if (campaignName == null || subject == null || senderEmail == null ||
                senderName == null || htmlContent == null || recipientListIds == null) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Missing required fields",
                    "required", List.of("campaignName", "subject", "senderEmail", "senderName", "htmlContent", "recipientListIds")
                ));
            }

            // Send campaign
            String response = brevoEmailCampaignService.sendEmailCampaign(
                    campaignName,
                    subject,
                    senderEmail,
                    senderName,
                    htmlContent,
                    recipientListIds,
                    scheduledAt
            );

            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Email campaign sent successfully",
                "response", response
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "success", false,
                "error", e.getMessage()
            ));
        }
    }
}
