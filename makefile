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
	  -Dsonar.login=81ae37cdc6e6784cbb337ca411fd90abbeada2b3 \
	  -Dsonar.user.dir=./tests/bwce1/tibco.bwce.sample.binding.rest.BookStore
#	  -Dsonar.java.binaries=./target
