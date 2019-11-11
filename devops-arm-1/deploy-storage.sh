#!/bin/bash

# Create an ARM deployment outside of DevOps, on my workstation.
# This working script was then ported to azure-pipelines.yml
# Chris Joakim, Microsoft, 2019/11/11

# azure-specific values
resource_group="cjoakim-arm3"
rg_region="eastus"

# construct unique time-based names for the deployment output files
name="storage"
epoch_time=`date +%s`
dep_name=""$epoch_time"-"$name"-deployment"

echo "resource_group:       "$resource_group
echo "epoch_time:           "$epoch_time
echo "AZURESPARMAPPID:      "$AZURESPARMAPPID
echo "AZURESPARMPASS:       "$AZURESPARMPASS
echo "AZURESPARMTENANT:     "$AZURESPARMTENANT

echo '=========='
echo 'az login...'
az login --service-principal \
  --username $AZURESPARMAPPID \
  --password $AZURESPARMPASS \
  --tenant   $AZURESPARMTENANT

echo '=========='
echo 'az group list...'
az group list 

echo 'Create the Resource Group (RG) with command: az group create...'
az group create --name $resource_group --location $rg_region

echo 'Deploy to the Resource Group (RG) with command: az group deployment create...'
az group deployment create \
  --name $dep_name \
  --resource-group $resource_group \
  --template-file storage-template.json \
  --parameters @storage-parameters.json
