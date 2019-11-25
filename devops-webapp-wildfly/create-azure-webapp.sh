#!/bin/bash

# Bash script which uses the Azure CLI (az) to:
# 1) Create a Resource Group (RG)
# 2) Create an App Service Plan in the RG
# 3) Create an Linux/Container Web App within the App Service Plan
# 4) Associate the Docker container created in this repo with the Web App
#
# See https://docs.microsoft.com/en-us/azure/app-service/containers/tutorial-custom-docker-image
#
# Chris Joakim, Microsoft, 2019/11/23

# Variables
rg="cjoakim-wildfly"
appName="cjoakim-wildfly"
planName="cjoakim-wildfly-plan"
location="EastUS"
registryPath="cjoakimacr.azurecr.io/azure-webapp-wildfly:latest"

echo '==='
echo 'Creating the Resource Group ...'
az group create --name $rg --location $location

echo '==='
echo 'Creating the App Service Plan ...'
az appservice plan create \
    --name $planName \
    --resource-group $rg \
    --location $location \
    --is-linux \
    --sku S1

echo '==='
echo 'Creating the Web App ...'
az webapp create \
    --resource-group $rg \
    --plan $planName \
    --name $appName \
    --deployment-container-image-name $registryPath \
    --docker-registry-server-password $AZURE_ACR_USER_PASS \
    --docker-registry-server-user $AZURE_ACR_USER_NAME \

echo '==='
echo 'Enabling Continuous Deployment (CD) for the Web App ...'
az webapp deployment container config \
    --enable-cd true \
    --name $appName \
    --resource-group $rg \

echo '==='
echo 'Listing the initial Web App environment variables ...'
az webapp config appsettings list  --name $appName --resource-group $rg

echo '==='
echo 'Set Web App environment variables from local environment variable values ...'
az webapp config appsettings set \
    --resource-group $rg \
    --name $appName \
    --settings AZURE_REDISCACHE_NAMESPACE=$AZURE_REDISCACHE_NAMESPACE

az webapp config appsettings set \
    --resource-group $rg \
    --name $appName \
    --settings AZURE_REDISCACHE_HOST=$AZURE_REDISCACHE_HOST

az webapp config appsettings set \
    --resource-group $rg \
    --name $appName \
    --settings AZURE_REDISCACHE_KEY=$AZURE_REDISCACHE_KEY

az webapp config appsettings set \
    --resource-group $rg \
    --name $appName \
    --settings AZURE_REDISCACHE_CONN_STRING=$AZURE_REDISCACHE_CONN_STRING

az webapp config appsettings set \
    --resource-group $rg \
    --name $appName \
    --settings AZURE_REDISCACHE_PORT=$AZURE_REDISCACHE_PORT

echo '==='
echo 'Listing the final Web App environment variables ...'
az webapp config appsettings list  --name $appName --resource-group $rg

echo '==='
echo 'Invoke the Web App at URL: '
echo http://$appName.azurewebsites.net
