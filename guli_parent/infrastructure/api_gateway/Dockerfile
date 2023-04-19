FROM openjdk:8
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./api_gateway-0.0.1-SNAPSHOT.jar ./gateway.jar
EXPOSE 8222
ENTRYPOINT ["java","-jar","-Xms100m","-Xmx100m"]
CMD ["gateway.jar"]
