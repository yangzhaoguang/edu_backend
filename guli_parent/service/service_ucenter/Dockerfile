FROM openjdk:8
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./service_ucenter-0.0.1-SNAPSHOT.jar ./service_ucenter.jar
EXPOSE 8160
ENTRYPOINT ["java","-jar","-Xms100m","-Xmx100m"]
CMD ["service_ucenter.jar"]
