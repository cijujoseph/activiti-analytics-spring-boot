FROM java:8-jre-alpine

MAINTAINER https://github.com/cijujoseph/activiti-analytics-spring-boot

COPY target/activiti-analytics-spring-boot-1.0.0-SNAPSHOT.jar /opt/activiti-analytics-spring-boot-1.0.0-SNAPSHOT.jar

CMD ["java","-jar","-Xms512m","-Xmx512m","/opt/activiti-analytics-spring-boot-1.0.0-SNAPSHOT.jar", "--spring.config.location=file:/conf/application.properties"]
