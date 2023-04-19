FROM openjdk:8
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./service_edu-0.0.1-SNAPSHOT.jar ./service_edu.jar
EXPOSE 8001
ENTRYPOINT ["java","-jar","-Xms100m","-Xmx100m"]
CMD ["service_edu.jar"]
