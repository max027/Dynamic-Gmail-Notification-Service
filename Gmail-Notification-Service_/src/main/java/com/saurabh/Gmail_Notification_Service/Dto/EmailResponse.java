package com.saurabh.Gmail_Notification_Service.Dto;

public class EmailResponse {
    private String status;
    private String message;
    private String error;

    public EmailResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public EmailResponse(String status, String error, boolean isError) {
        this.status = status;
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
