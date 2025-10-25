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

Run (start MySQL via docker-compose if you want):
1. docker-compose up -d
2. mvn clean package
3. mvn spring-boot:run

Tests:
- mvn test (tests use H2 in-memory)

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



