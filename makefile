build: 
	mvn clean package 
	cp target/sonar-bw-6-plugin-0.0.3-SNAPSHOT.jar /var/opt/tibco/software/sonarqube/plugins/

test1: 
	sonar-scanner -X\
	  -Dsonar.projectKey=BWCE-Test1 \
	  -Dsonar.sources=/Users/markbloomfield/Documents/projects/devOpsToolkit/sourcerepo/sonar-bw6/tests/bwce1/tibco.bwce.sample.binding.rest.BookStore \
	  -Dsonar.host.url=http://192.168.76.20:9000 \
	  -Dsonar.login=982d1fb58a6132be444529416f4d7536d3cefe12 \
	  -Dsonar.user.dir=./tests/bwce1/tibco.bwce.sample.binding.rest.BookStore
#	  -Dsonar.java.binaries=./target