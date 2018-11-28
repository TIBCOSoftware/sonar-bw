build: 
	mvn clean package 
	cp target/sonar-bw-6-plugin-0.0.3-SNAPSHOT.jar /var/opt/tibco/software/sonarqube/plugins/