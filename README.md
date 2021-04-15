**SLAMBOOK:**
<br><br>
SLAMBOOK built on Spring Boot is a web based application used by user for storing details of their friends.
<br>
Basic CRUD functinality is used.
<br><br>
**Run locally**
<br>
To run this application locally, you need :
<br>
-To have Java JDK 8 or above  installed and an IDE to import the project.<br>
-MySQL installed in your system.<br>
-After opening the IDE import this project as Existing Maven Project.
<br><br>

**There are few changes to be made in \src\main\resources\application.properties file**
<br>
-spring.datasource.url=jdbc:mysql://localhost/{enter the name of DB to be used }
<br>
-spring.datasource.username={username credentials for your project}
<br>
-spring.datasource.password={ password for that username}
<br><br>

**Make changes in EmailService File providing details of your EmailId & Password.In order to use gmail api for sending OTP**
<br>
Your app should be up and running on localhost:8080
<br><br>
**Tech Stack**
<br>
-Spring Boot Framework.<br>
-MySQL database.<br>
-HTML, CSS, Bootstrap and Thymeleaf for front end.
