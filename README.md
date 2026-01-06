# Email Notification Service

A Spring Boot REST API that sends emails using Gmail with fully dynamic configuration loaded from a database and complete email logging.

## Features

- Single REST endpoint for sending emails
- Dynamic email configuration from database
- **Complete email logging** - Every email sent/failed is logged to database
- No hardcoded values
- Input validation
- Comprehensive error handling and logging
- Support for Gmail with App Passwords

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Gmail account with App Password enabled
- Database (MySQL/PostgreSQL)

## Gmail Setup

1. Enable 2-Factor Authentication on your Gmail account
2. Generate an App Password:
    - Go to https://myaccount.google.com/apppasswords
    - Select "Mail" and your device
    - Copy the generated 16-character password

## Installation
1. Clone the repository
2. Update `application.properties` with your database credentials
3. Insert email configuration into the database:
```sql
INSERT INTO email_config (
    smtp_host, smtp_port, username, password, 
    smtp_auth, smtp_starttls_enable, protocol, is_active
) VALUES (
    'smtp.gmail.com', 587, 'your-email@gmail.com', 'your-app-password',
    true, true, 'smtp', true
);
```

4. Build and run:
```bash
mvn clean install
mvn spring-boot:run
```

## API Endpoints

### 1. Send Email Notification

**Endpoint:** `POST /api/sendNotification`

**Request Body:**
```json
{
  "tomail": "recipient@example.com",
  "subject": "Test Email",
  "body": "This is a test email"
}
```

**Success Response:**
```json
{
  "status": "SUCCESS",
  "message": "Email sent successfully"
}
```

**Error Response:**
```json
{
  "status": "FAILED",
  "error": "Unable to send email"
}
```

### 2. Get All Email Logs

**Endpoint:** `GET /api/email-logs`

**Response:**
```json
[
  {
    "id": 1,
    "toEmail": "recipient@example.com",
    "subject": "Test Email",
    "body": "Email content",
    "status": "SUCCESS",
    "errorMessage": null,
    "sentAt": "2024-01-06T10:30:00",
    "sentBy": "sender@gmail.com"
  }
]
```



## Testing with Postman

### Send Email
1. Set method to POST
2. URL: `http://localhost:8080/api/sendNotification`
3. Headers: `Content-Type: application/json`
4. Body (raw JSON):
```json
{
  "tomail": "test@example.com",
  "subject": "Test Subject",
  "body": "Test Body Content"
}
```

## Database Schema

### email_config Table
Stores SMTP configuration for sending emails.

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key |
| smtp_host | VARCHAR(255) | SMTP server address |
| smtp_port | INT | SMTP port |
| username | VARCHAR(255) | Email account |
| password | VARCHAR(255) | App password |
| smtp_auth | BOOLEAN | Enable authentication |
| smtp_starttls_enable | BOOLEAN | Enable TLS |
| protocol | VARCHAR(50) | Email protocol |
| is_active | BOOLEAN | Active configuration flag |

### email_log Table
Stores all email sending attempts with status and details.

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key |
| to_email | VARCHAR(255) | Recipient email |
| subject | VARCHAR(500) | Email subject |
| body | TEXT | Email body content |
| status | VARCHAR(50) | SUCCESS or FAILED |
| error_message | VARCHAR(1000) | Error details if failed |
| sent_at | TIMESTAMP | Timestamp of attempt |
| sent_by | VARCHAR(255) | Sender email account |

## Database Configuration

### MySql
The application uses MySql database by default.
- URL: http://localhost:8080/email_service
- JDBC URL: `jdbc:mysql://localhost:3306/email_service`
- Username: (username)
- Password: (password)

Add MySQL dependency in `pom.xml`.


## Email Logging Details

Every email attempt (successful or failed) is automatically logged with:
- Recipient email address
- Subject and body content
- Status (SUCCESS/FAILED)
- Error message (if failed)
- Timestamp of attempt
- Sender email account used

This provides complete audit trail and troubleshooting capability.


## Error Handling

The service handles:
- Input validation errors (400 Bad Request)
- Missing email configuration (500 Internal Server Error)
- Email sending failures (500 Internal Server Error)
- All errors are logged with detailed information
- **All email attempts are saved to database regardless of success/failure**

## Troubleshooting

1. **Email not sending**: Verify Gmail App Password is correct
2. **Authentication failed**: Ensure 2FA is enabled and App Password is used
3. **Database connection error**: Check database credentials in `application.properties`
4. **No active configuration**: Verify `is_active = true` in the database
5. **Logs not saving**: Check database connection and email_log table exists

## License

MIT License

## Author
Name: Saurabh Wajage
Mail: saurabhwajage887@gmail.com