FROM openjdk:8
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./service_vod-0.0.1-SNAPSHOT.jar ./service_vod.jar
EXPOSE 8003
ENTRYPOINT ["java","-jar","-Xms100m","-Xmx100m"]
CMD ["service_vod.jar"]
