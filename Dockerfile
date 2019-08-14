
FROM java:8

VOLUME /tmp

COPY target/*.jar app.jar

EXPOSE ${CART_MS_PORT}

ENTRYPOINT ["java","-jar","/app.jar"]