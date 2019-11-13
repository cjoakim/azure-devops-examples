#!/bin/bash

# Create an ARM deployment outside of DevOps, on my workstation.
# Chris Joakim, Microsoft, 2019/11/13

# azure-specific values
resource_group="cjoakim-appinsights"
rg_region="eastus"

# construct unique time-based names for the deployment output files
name="appinsights"
epoch_time=`date +%s`
dep_name=""$epoch_time"-"$name"-deployment"

echo '=========='
echo 'az group list...'
az group list 

echo 'Create the Resource Group (RG) with command: az group create...'
az group create --name $resource_group --location $rg_region

echo 'Deploy to the Resource Group (RG) with command: az group deployment create...'
az group deployment create \
  --name $dep_name \
  --resource-group $resource_group \
  --template-file appinsights2-template.json \
  --parameters @appinsights2-parameters.json
