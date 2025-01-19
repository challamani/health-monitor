# health-monitor

### Build the spring boot application
```shell
./mvnw package
```

### Build the docker image
```shell
docker build -t healthcheck-monitor:latest .
```

### Run the docker container
```shell
docker run -d -p 8080:8080 \
    -e HEALTH_ENDPOINT=https://httpbin.org/status/201 \
    -e HEALTH_INTERVAL=30 \
    -e HEALTH_EXPECTEDSTATUS=201 \
    healthcheck-monitor:latest
```

### Push the image into minikube node.
```shell
minikube image load healthcheck-monitor:latest
```