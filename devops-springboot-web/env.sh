#!/bin/bash

# Bash script to set "Spring-JPA-friendly" environment variable names.
#
# You're apt to get this error if these environment variables aren't set:
# Caused by: org.springframework.boot.context.properties.bind.BindException: 
# Failed to bind properties under 'azure.cosmosdb' to com.microsoft.azure.spring.autoconfigure.cosmosdb.CosmosDBProperties
# 
# Class com.chrisjoakim.azure.airports.data.cosmos.AirportRepository, a Spring JPA 
# ReactiveCosmosRepository instance, uses these three environment variables.
#
# Chris Joakim, 2019/11/05

export AZURE_COSMOSDB_DATABASE=$AZURE_COSMOSDB_SQLDB_DBNAME
export AZURE_COSMOSDB_URI=$AZURE_COSMOSDB_SQLDB_URI
export AZURE_COSMOSDB_KEY=$AZURE_COSMOSDB_SQLDB_KEY

env | grep  AZURE_COSMOSDB_ | sort 
