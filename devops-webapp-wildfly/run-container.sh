#!/bin/bash

# Execute the Docker container containing the web application.
# Chris Joakim, Microsoft, 2019/11/23

echo 'docker ps before ...'
docker ps

echo 'running container ...'
docker run -d \
    -e AZURE_REDISCACHE_HOST=$AZURE_REDISCACHE_HOST \
    -e AZURE_REDISCACHE_KEY=$AZURE_REDISCACHE_KEY \
    -p 3000:8080 \
    cjoakim/azure-webapp-wildfly:latest

echo 'docker ps after ...'
docker ps
