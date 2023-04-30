From openjdk:8
copy ./target/spring-boot-security-login-0.0.1-SNAPSHOT.jar spring-boot-security-login-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","spring-boot-security-login-0.0.1-SNAPSHOT.jar"]