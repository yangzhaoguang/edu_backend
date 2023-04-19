FROM openjdk:8
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./service_oss-0.0.1-SNAPSHOT.jar ./service_oss.jar
EXPOSE 8002
ENTRYPOINT ["java","-jar","-Xms100m","-Xmx100m"]
CMD ["service_oss.jar"]
