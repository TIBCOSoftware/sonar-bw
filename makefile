build: 
	mvn clean package
	rm docker/extensions/*
	cp target/sonar-bw-plugin-*.jar docker/extensions
	docker build --tag=sonarqube-custom docker/.

docker-run:
	docker run -p 9000:9000 -ti sonarqube-custom:latest

check: 
	mvn clean verify sonar:sonar \
	  -Dsonar.projectKey=bw6-plugin \
	  -Dsonar.projectName='bw6-plugin' \
	  -Dsonar.host.url=http://localhost:9000 \
	  -Dsonar.token=sqp_f9b00fec0e4ea0aac87cd1682bae48b8a082d250