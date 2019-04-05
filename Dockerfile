FROM java:8u111-jre
COPY target/product-0.0.1-SNAPSHOT.jar product-0.0.1-SNAPSHOT.jar
COPY key key
ENTRYPOINT ["java","-jar","product-0.0.1-SNAPSHOT.jar"]