build: 
	mvn clean package 
	cp target/sonar-bw-6-plugin-1.3.9.jar /var/opt/tibco/software/sonarqube/plugins/

copytovagrant:
# TODO THIS DOES NOT WORK.
	scp /var/opt/tibco/software/sonarqube/plugins/ vagrant@192.168.76.20:/path/to/file

test1: 
	sonar-scanner -X\
	  -Dsonar.projectKey=BWCE-Test1 \
	  -Dsonar.sources=./tests/bwce1/tibco.bwce.sample.binding.rest.BookStore \
	  -Dsonar.host.url=http://192.168.76.20:9000 \
	  -Dsonar.login=d738135f812a3985e4b3a02704e4a08262dce2be \
	  -Dsonar.user.dir=./tests/bwce1/tibco.bwce.sample.binding.rest.BookStore
#	  -Dsonar.java.binaries=./target
