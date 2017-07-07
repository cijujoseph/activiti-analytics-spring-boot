# activiti-analytics-spring-boot
Activiti must be running with both history and events turned on to have analytics data

* Change the application.properties to match your activiti installation

* Currently tested only against Activiti running on H2 or Postgres databases. Queries in properties file and ProcessBatchPreparation.java may need to be altered if using a different database.

* Run using the following command 
	`mvn clean spring-boot:run `