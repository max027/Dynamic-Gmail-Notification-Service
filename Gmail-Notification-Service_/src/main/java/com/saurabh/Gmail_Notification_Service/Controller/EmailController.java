package com.saurabh.Gmail_Notification_Service.Controller;

import com.saurabh.Gmail_Notification_Service.Dto.EmailRequest;
import com.saurabh.Gmail_Notification_Service.Dto.EmailResponse;
import com.saurabh.Gmail_Notification_Service.Service.EmailService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class EmailController {
    private static final Logger logger= LoggerFactory.getLogger(EmailController.class);

    private final EmailService service;

    @Autowired
    public EmailController(EmailService emailService){
        this.service=emailService;
    }

    @PostMapping("/sendNotification")
    public ResponseEntity<EmailResponse> sendNotification(@Valid @RequestBody EmailRequest request){
        logger.info("Received email request:"+request.getTomail());
        try{
            service.sendEmail(request.getTomail(),request.getSubject(),request.getBody());
            logger.info("Email send successfully to:"+request.getTomail());

            return ResponseEntity.ok(new EmailResponse("SUCCESS","Email sent successfully"));

        } catch (Exception e) {
            logger.error("Failed to send email to:"+request.getTomail()+" Error:"+e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EmailResponse("FAILED","Unable to send"));
        }
    }
}
