FROM openjdk:8-jdk-alpine
RUN  apk update && apk upgrade && apk add netcat-openbsd
RUN  mkdir -p /usr/local/app
ADD  target/springboot-airports-web-0.0.1.jar /usr/local/app/
ADD  run.sh run.sh
RUN  chmod +x run.sh

EXPOSE 80

CMD  java -Xmx200m -Dspring.profiles.active=web -jar /usr/local/app/springboot-airports-web-0.0.1.jar

# docker build -t cjoakim/azure-springboot-airports-web . 
# docker run -d -e xxx=yyy -p 3001:3000 cjoakim/azure-springboot-airports-web:latest
# curl -v curl http://localhost:3001/health/env
# docker image ls
# docker ps
# docker stop -t 2 008038664a58
#
# DockerHub:
# docker push cjoakim/azure-springboot-airports-web:latest
#
# Azure:
# az acr login --name cjoakimacr
# az acr repository list --name cjoakimacr --output table
# docker tag cjoakim/azure-springboot-airports-web:latest cjoakimacr.azurecr.io/azure-springboot-airports-web:latest
# docker push cjoakimacr.azurecr.io/azure-springboot-airports-web:latest
#
# trivial change made here 