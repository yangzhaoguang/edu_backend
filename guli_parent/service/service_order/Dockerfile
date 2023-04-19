FROM openjdk:8
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./service_order-0.0.1-SNAPSHOT.jar ./service_order.jar
EXPOSE 8007
ENTRYPOINT ["java","-jar","-Xms100m","-Xmx100m"]
CMD ["service_order.jar"]
