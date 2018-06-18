# SpringSecurityXMLConfigurationExample
Demonstrates use of adding authentication and authorization to web application using Spring Security with Security Namespace Configuration. Features in the project:
1.	Two login forms with different form fields.

- Login via Password(default): User Name, Password, Remember-Me option.

- Login via Verification Code: Cellphone No., Verification Code.

2.	CSRF protection.
3.	Remember-me authentication.
4.	Role-based authorization and authority-base authorization.
5.	JUnit test with an embedded HSQL database.

## How to compile?
```
mvn clean install
```
## How to test?
```
mvn clean test
```
## How to run
1. Create tables in your database and add test data.

- SQL statements to create tables for the project: [schema.sql](src/test/resources/database/schema.sql).

- SQL Statements to add test data: [test-data.sql](src/test/resources/database/test-data.sql).

    The following accounts are available:

    | User Name | Password | Cellphone No. | Roles | Authorities |
    | --- | --- | --- | --- | --- |
    | MichaelJackson | hello | 2011111111 | USER | READ |
    | TaylorSwift | hello | 2011111112 | ADMIN | READWRITE |
    | SarahBrightman | password | 2011111113 | OPERATOR | WRITE |

2. Update [database configuration](src/main/resources/spring/db.properties) to reflect your own settings.

3. Compile the project.

4. Deploy target/SpringSecurityXMLConfigurationExample.war to the web container of your choice and visit the homepage. e.g. In my local environment the URL is: http://localhost:8080/SpringSecurityXMLConfigurationExample/

  - Click "Login" on homepage and enter User Name/Password(default option) or Cellphone No./Verification Code to experience the authentication and authorization features. Please note that verification code is currently hard-coded to "1984" for demo purposes.

  - Remember Me Authentication is available for User Name/Password authentication only. By checking the "Remember Me" option on the login form, a Cookie named "springdemoRememberMe" will be created and the user will automatically log into the system in the next week after a successful login.

  - Logout will clear session context and any remember me selection. 
