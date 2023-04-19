FROM openjdk:8
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./service_msm-0.0.1-SNAPSHOT.jar ./service_msm.jar
EXPOSE 8006
ENTRYPOINT ["java","-jar","-Xms100m","-Xmx100m"]
CMD ["service_msm.jar"]
