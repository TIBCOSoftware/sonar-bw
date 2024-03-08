build: 
	mvn clean package
	rm docker/extensions/*
	cp target/sonar-bw-6-plugin-*.jar docker/extensions
	docker build --tag=sonarqube-custom docker/.

docker-run:
	docker run -p 9000:9000 -ti sonarqube-custom:latest

check: 
	mvn clean verify sonar:sonar \
	  -Dsonar.projectKey=bw6-plugin \
	  -Dsonar.projectName='bw6-plugin' \
	  -Dsonar.host.url=http://localhost:9000 \
	  -Dsonar.token=sqp_fe0e51fa5bcc4f208b7a3e2f1d67d095cf6cf842
