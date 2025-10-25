# Dual-auth Spring Boot (Email + Mobile OTP)

This project implements:
- Email-based authentication (email + password) -> email_jwt
- Mobile OTP authentication -> mobile_jwt
- Separate JWT secrets for email and mobile

Database: MySQL (configured to use database `dual_auth_db` on localhost)

Generated JWT secrets (32 chars):
- email secret: bpii18yPMIAEfufqygwAwELEii2Shlfp
- mobile secret: PSsO8iAkBseYt4IeI0EQohDpUIANBXmD

MySQL credentials used in application.yml:
- username: root
- password: your_Password

## Prerequisites

- Java 17
- Maven
- MySQL Server running on `localhost`
- MySQL database: `dual_auth_db`
- 
Run Application:
1. Clone the repository:
   - git clone <repository_url>
2. Build the project:
   - mvn clean package
3. Run the Spring Boot application:
   - mvn spring-boot:run
4. application will start on http://localhost:<port_number>

Tests:
- mvn test (tests use H2 in-memory)

API endpoints:

Email
- POST: /api/auth/email/register
- POST: /api/auth/email/login

Mobile
- POST: /api/auth/mobile/request-otp
- POST: /api/auth/mobile/verify-otp

ScreenShort:
1. Mobile otp request
 <img width="1379" height="884" alt="image" src="https://github.com/user-attachments/assets/e0231b9d-25a6-4728-9cdd-725a5d6c087a" />
2. Otp verfiy then it will generate new token for mobile user
 <img width="1413" height="864" alt="image" src="https://github.com/user-attachments/assets/77d6c456-bd74-48bf-8e33-336d79a1fe34" />

Email
1. Email register
<img width="1421" height="886" alt="image" src="https://github.com/user-attachments/assets/c202244c-17a7-4c20-be25-f405d62d376e" />
2. Email token generate
<img width="1438" height="904" alt="image" src="https://github.com/user-attachments/assets/959d2a60-0212-45da-929c-f347f11f4925" />



 
Notes:
- OTPs are returned in API response for testing only.
- Secrets in application.yml are for convenience; in production use environment variables or a secrets manager.



