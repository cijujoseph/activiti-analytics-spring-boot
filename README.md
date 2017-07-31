# activiti-analytics-spring-boot
Activiti must be running with both history and events turned on to have analytics data

* Change the application.properties to match your activiti installation

* Currently tested only against Activiti running on H2 or Postgres databases. Queries in properties file and ProcessBatchPreparation.java may need to be altered if using a different database.

* If you are using Oracle DB, do the following to include oracle jdbc driver
Download the Oracle jdbc driver and run the following command to add drive to your local maven repository:

```
mvn install:install-file -DgroupId=<groupId> -DartifactId=<artifactId> \
-Dversion=<version> -Dpackaging=jar -Dfile=<jar-file-name and location> -DgeneratePom=true

```
Eg: command and pom.xml entry:
```
mvn install:install-file -DgroupId=com.oracle.jdbc -DartifactId=ojdbc8 \
-Dversion=12.2.0.1 -Dpackaging=jar -Dfile=ojdbc8.jar -DgeneratePom=true

```
```
		<dependency>
			<groupId>com.oracle.jdbc</groupId>
			<artifactId>ojdbc8</artifactId>
			<version>12.2.0.1</version>
		</dependency>
```

* Run using the following command 
	
	`mvn clean spring-boot:run `
	
* Running using the packaged jar
	
	`mvn clean package`
	
	`java -jar target/activiti-analytics-spring-boot-1.0.0-SNAPSHOT.jar`
	
* The app brings up an elasticsearch instance on startup. If you want to use a standalone elasticsearch, comment out the elasticsearch dependency in pom.xml.
* If using the default elasticsearch with this app, the version number is 2.4.5 (this is dictated by the elastic springboot starter). If you are looking for a compatible Kibana version -> download 4.6.3 from https://www.elastic.co/downloads/past-releases/kibana-4-6-3