#!/bin/bash

# Execute the Docker container locally, injecting the necessary
# environment variables from workstation environment variables.
# Chris Joakim, 2019/11/05

docker run -d \
    -e AZURE_COSMOSDB_SQLDB_URI=$AZURE_COSMOSDB_SQLDB_URI \
    -e AZURE_COSMOSDB_SQLDB_KEY=$AZURE_COSMOSDB_SQLDB_KEY \
    -e AZURE_COSMOSDB_SQLDB_DBNAME=$AZURE_COSMOSDB_SQLDB_DBNAME \
    -e AZURE_COSMOSDB_URI=$AZURE_COSMOSDB_SQLDB_URI \
    -e AZURE_COSMOSDB_KEY=$AZURE_COSMOSDB_SQLDB_KEY \
    -e AZURE_COSMOSDB_DATABASE=$AZURE_COSMOSDB_SQLDB_DBNAME \
    -p 3000:80 \
    cjoakim/azure-springboot-airports-web:latest
