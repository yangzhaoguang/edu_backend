FROM openjdk:8
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./service_cms-0.0.1-SNAPSHOT.jar ./service_cms.jar
EXPOSE 8004
ENTRYPOINT ["java","-jar","-Xms100m","-Xmx100m"]
CMD ["service_cms.jar"]
