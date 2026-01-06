package com.saurabh.Gmail_Notification_Service.Service;

import com.saurabh.Gmail_Notification_Service.Models.EmailConfig;
import com.saurabh.Gmail_Notification_Service.Models.EmailLogs;
import com.saurabh.Gmail_Notification_Service.Repo.EmailConfigRepo;
import com.saurabh.Gmail_Notification_Service.Repo.EmailLogsRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Properties;

@Service
public class EmailService {
    private static final Logger logger= LoggerFactory.getLogger(EmailService.class);

    private final EmailConfigRepo emailConfigRepo;

    private final EmailLogsRepo emailLogsRepo;


    @Autowired
    public EmailService(EmailConfigRepo emailConfigRepo,EmailLogsRepo emailLogsRepo){
        this.emailConfigRepo=emailConfigRepo;
        this.emailLogsRepo=emailLogsRepo;
    }


    @Transactional
    public void sendEmail(String toEmail,String subject,String body)throws MessagingException {
        EmailConfig config=emailConfigRepo.findByIsActiveTrue().orElseThrow(
                ()->new RuntimeException("No active email configuration found")
        );
        logger.info("loading email configuration for user:"+config.getUsername());

        EmailLogs emailLogs=new EmailLogs();
        emailLogs.setToEmail(toEmail);
        emailLogs.setBody(body);
        emailLogs.setSubject(subject);
        emailLogs.setSentBy(config.getUsername());

        try{
            JavaMailSender sender=createMailSender(config);
            MimeMessage message=sender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message,true,"UTF-8");
            helper.setFrom(config.getUsername());
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body,true);

            sender.send(message);

            emailLogs.setSentBy(config.getUsername());
            emailLogs.setErrorMessage(null);
        } catch (Exception e) {
            emailLogs.setStatus("FAILED");
            emailLogs.setErrorMessage(e.getMessage());
            logger.error("Failed to send message to "+toEmail+" Error:"+e.getMessage()+" "+e);
            throw e;
        }finally {
            emailLogs.setSentAt(LocalDateTime.now());
            emailLogs.setStatus("SUCCESS");
            emailLogsRepo.save(emailLogs);
            logger.info("Email log saved with status:"+emailLogs.getStatus());
        }

    }

    private JavaMailSender createMailSender(EmailConfig config){
        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();

        javaMailSender.setHost(config.getSmtpHost());
        javaMailSender.setPort(config.getSmtpPort());
        javaMailSender.setUsername(config.getUsername());
        javaMailSender.setPassword(config.getPassword());
        javaMailSender.setProtocol(config.getProtocol());

        Properties props=javaMailSender.getJavaMailProperties();
        props.put("mail.smtp.auth",config.getSmtpAuth().toString());
        props.put("mail.smtp.starttls.enable",config.getSmtpStarttlsEnable().toString());
        props.put("mail.smtp.starttls.required","true");
        props.put("mail.smtp.ssl.trust",config.getSmtpHost());
        logger.debug("Mail sender configured with host: {} and port: {}",config.getSmtpHost(),config.getSmtpPort());

        return javaMailSender;

    }


}

