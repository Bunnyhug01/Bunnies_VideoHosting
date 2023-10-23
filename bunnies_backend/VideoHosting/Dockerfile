FROM openjdk:20-jdk
COPY target/*.jar /home/app/application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/home/app/application.jar"]