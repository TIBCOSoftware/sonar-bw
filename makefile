build: 
	mvn clean package 
	cp target/sonar-bw-6-plugin-0.0.3-SNAPSHOT.jar /var/opt/tibco/software/sonarqube/plugins/

copytovagrant:
# TODO THIS DOES NOT WORK.
	scp /var/opt/tibco/software/sonarqube/plugins/ vagrant@192.168.76.20:/path/to/file

test1: 
	sonar-scanner -X\
	  -Dsonar.projectKey=BWCE-Test1 \
	  -Dsonar.sources=./tests/bwce1/tibco.bwce.sample.binding.rest.BookStore \
	  -Dsonar.host.url=http://192.168.76.20:9000 \
	  -Dsonar.login=2df09a92b48b40ca55f7798b5ce465c511006e0b \
	  -Dsonar.user.dir=./tests/bwce1/tibco.bwce.sample.binding.rest.BookStore
#	  -Dsonar.java.binaries=./target
