FROM java:8
VOLUME /tmp
ADD target/product-0.0.1-SNAPSHOT.jar product-0.0.1-SNAPSHOT.jar
ADD key key
ENTRYPOINT ["java","-jar","product-0.0.1-SNAPSHOT.jar"]