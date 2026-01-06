package com.saurabh.Gmail_Notification_Service.Repo;

import com.saurabh.Gmail_Notification_Service.Models.EmailLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailLogsRepo extends JpaRepository<EmailLogs,Long> {

}
