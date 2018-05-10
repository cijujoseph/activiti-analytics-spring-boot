## Upgraded the project to work with Elastic 6.x...readme is yet to be updated with the new kibana setup and conf. This change might cause the master branch to not work with older versions of ES.

# activiti-analytics-spring-boot
Activiti must be running with both history and events turned on to have analytics data

* Change the application.properties to match your activiti installation

* Currently tested only against Activiti running on H2, Oracle, MySQL or Postgres databases. Queries in properties file and ProcessBatchPreparation.java may need to be altered if using a different database.

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
	
 * Run using docker (run command is just an example, adjust as per your env)
 
 	`mvn clean package`

	`docker build -t activiti-analytics-spring-boot .`

	`docker run --name aps-analytics -v /tmp/activiti-analytics-spring-boot/confs:/conf --rm  --net mydockernetwork  activiti-analytics-spring-boot`

 
	
* The app brings up an elasticsearch instance on startup. If you want to use a standalone elasticsearch, comment out the elasticsearch dependency in pom.xml.
* If using the default elasticsearch with this app, the version number is 2.4.5 (this is dictated by the elastic springboot starter). If you are looking for a compatible Kibana version -> download 4.6.3 from https://www.elastic.co/downloads/past-releases/kibana-4-6-3


# Set up some sample visualizations.
Once you have Kibana running open http://localhost:5601/ (default host and port of Kibana unless you changed it) in your browser.

Create an index pattern "bpmanalyticseventlog-\*" as shown below. (Settings -> Indices -> bpmanalyticseventlog-\* -> Create
![Index Pattern 1](images/index-pattern-1.png)
![Index Pattern 1](images/index-pattern-2.png)


### Import sample dashboard and visualizations
1.	Import a task search object using the JSON file "task-search-kibana-4.6.3.json" present in kibana folder. Settings -> Objects -> Searches -> Import -> task-search-kibana-4.6.3.json
2.	After importing the task search, import the visualizations and dashboard built on top of the imported search using the file "task-dashboard-kibana-4.6.3.json". Settings -> Objects -> Searches -> Import ->-> task-dashboard-kibana-4.6.3.json
3.	Once you have done the above two steps, you now should have a task dashboard in your kibana as shown below

![Task Dashboard](images/task-dashboard.png)

Similarly you can create more task/process dashboards and visualizations in Kibana!! It is now your turn to explore more!!
