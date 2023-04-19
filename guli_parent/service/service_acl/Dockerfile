FROM openjdk:8
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./service_acl-0.0.1-SNAPSHOT.jar ./service_acl.jar
EXPOSE 8009
ENTRYPOINT ["java","-jar","-Xms100m","-Xmx100m"]
CMD ["service_acl.jar"]
