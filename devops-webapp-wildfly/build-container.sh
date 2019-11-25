#!/bin/bash

# Create the Docker container which contains the root.war file.
# Chris Joakim, Microsoft, 2019/11/23

cd docker-dir

echo 'building ...'
docker build -t cjoakim/azure-webapp-wildfly .

echo 'listing image ...'
docker images | grep azure-webapp-wildfly

cd ..
