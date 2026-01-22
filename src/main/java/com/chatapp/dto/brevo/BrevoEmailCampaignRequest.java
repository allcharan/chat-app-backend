package com.chatapp.dto.brevo;

import java.util.List;

public class BrevoEmailCampaignRequest {
    private String name;
    private String subject;
    private Sender sender;
    private String type;
    private String htmlContent;
    private Recipients recipients;
    private String scheduledAt;

    public BrevoEmailCampaignRequest() {}

    public BrevoEmailCampaignRequest(String name, String subject, String senderEmail, String senderName,
                                     String htmlContent, List<Long> listIds, String scheduledAt) {
        this.name = name;
        this.subject = subject;
        this.sender = new Sender(senderEmail, senderName);
        this.type = "classic";
        this.htmlContent = htmlContent;
        this.recipients = new Recipients(listIds);
        this.scheduledAt = scheduledAt;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public Sender getSender() { return sender; }
    public void setSender(Sender sender) { this.sender = sender; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getHtmlContent() { return htmlContent; }
    public void setHtmlContent(String htmlContent) { this.htmlContent = htmlContent; }

    public Recipients getRecipients() { return recipients; }
    public void setRecipients(Recipients recipients) { this.recipients = recipients; }

    public String getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(String scheduledAt) { this.scheduledAt = scheduledAt; }

    // Inner class for sender
    public static class Sender {
        private String email;
        private String name;

        public Sender() {}
        public Sender(String email, String name) {
            this.email = email;
            this.name = name;
        }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    // Inner class for recipients
    public static class Recipients {
        private List<Long> listIds;

        public Recipients() {}
        public Recipients(List<Long> listIds) {
            this.listIds = listIds;
        }

        public List<Long> getListIds() { return listIds; }
        public void setListIds(List<Long> listIds) { this.listIds = listIds; }
    }
}
