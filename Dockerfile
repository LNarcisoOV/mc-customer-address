FROM openjdk:17-oracle
EXPOSE 8080
ADD /target/mc-customer-address-0.0.1-SNAPSHOT.jar mc-customer-address.jar
ENTRYPOINT ["java", "-jar", "mc-customer-address.jar"]
