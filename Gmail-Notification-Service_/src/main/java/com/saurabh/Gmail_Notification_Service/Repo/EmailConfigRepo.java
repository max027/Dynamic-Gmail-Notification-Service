package com.saurabh.Gmail_Notification_Service.Repo;

import com.saurabh.Gmail_Notification_Service.Models.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailConfigRepo extends JpaRepository<EmailConfig,Long> {
    Optional<EmailConfig> findByIsActiveTrue();
}
