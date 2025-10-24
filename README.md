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
- password: abhi@18

Run (start MySQL via docker-compose if you want):
1. docker-compose up -d
2. mvn clean package
3. mvn spring-boot:run

Tests:
- mvn test (tests use H2 in-memory)

Notes:
- OTPs are returned in API response for testing only.
- Secrets in application.yml are for convenience; in production use environment variables or a secrets manager.
