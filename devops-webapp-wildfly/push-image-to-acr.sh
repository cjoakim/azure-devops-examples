#!/bin/bash

# Tag and Push the Docker image to Azure Container Registry (ACR)
# Chris Joakim, Microsoft, 2019/11/23

az acr login \
    --name     $AZURE_ACR_USER_NAME \
    --username $AZURE_ACR_USER_NAME \
    --password $AZURE_ACR_USER_PASS

echo 'listing current contents of the registry ...'
az acr repository list --name cjoakimacr --output table

echo 'showing the current version of the image ...'
az acr repository show --name cjoakimacr --image azure-webapp-wildfly:latest

echo 'tagging the image ...'
docker tag cjoakim/azure-webapp-wildfly:latest cjoakimacr.azurecr.io/azure-webapp-wildfly:latest

echo 'pushing the image to ACR ...'
docker push cjoakimacr.azurecr.io/azure-webapp-wildfly:latest

echo 'listing latest contents of the registry ...'
az acr repository list --name cjoakimacr --output table

echo 'showing the updated version of the image ...'
az acr repository show --name cjoakimacr --image azure-webapp-wildfly:latest
