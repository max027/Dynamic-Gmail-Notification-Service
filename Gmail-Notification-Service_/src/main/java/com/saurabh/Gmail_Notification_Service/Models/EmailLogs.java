package com.saurabh.Gmail_Notification_Service.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_log")
public class EmailLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String toEmail;

    @Column(nullable = false, length = 500)
    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false)
    private String status; // SUCCESS, FAILED

    @Column(length = 1000)
    private String errorMessage;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    @Column
    private String sentBy; // Email account used to send

    public EmailLogs() {
    }

    public EmailLogs(String toEmail, String subject, String body, String status, String errorMessage, String sentBy) {
        this.toEmail = toEmail;
        this.subject = subject;
        this.body = body;
        this.status = status;
        this.errorMessage = errorMessage;
        this.sentBy = sentBy;
        this.sentAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }
}
