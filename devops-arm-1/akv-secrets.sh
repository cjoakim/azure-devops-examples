#!/bin/bash

# Set Azure KeyVault secrets with the Azure CLI.
# The secrets are my Azure Container Registry values/secrets, so that
# Azure DevOps Pipelines can read these and use them to deploy ARM
# templates to my subscription.
#
# Chris Joakim, Microsoft, 2019/11/11

# az login   <-- run this first

echo 'setting the secrets in AKV ...'

az keyvault secret set \
    --vault-name $AZURE_AKV_NAME \
    --name  AZURESPARMAPPID \
    --value $AZURESPARMAPPID

az keyvault secret set \
    --vault-name $AZURE_AKV_NAME \
    --name  AZURESPARMDISPLAYNAME \
    --value $AZURESPARMDISPLAYNAME

az keyvault secret set \
    --vault-name $AZURE_AKV_NAME \
    --name  AZURESPARMNAME \
    --value $AZURESPARMNAME

az keyvault secret set \
    --vault-name $AZURE_AKV_NAME \
    --name  AZURESPARMPASS \
    --value $AZURESPARMPASS

az keyvault secret set \
    --vault-name $AZURE_AKV_NAME \
    --name  AZURESPARMTENANT \
    --value $AZURESPARMTENANT

echo 'listing the secrets in AKV ...'

az keyvault secret list --vault-name $AZURE_AKV_NAME

echo 'showing the secrets in AKV ...'

az keyvault secret show \
    --vault-name $AZURE_AKV_NAME \
    --name  AZURESPARMAPPID 

az keyvault secret show \
    --vault-name $AZURE_AKV_NAME \
    --name  AZURESPARMDISPLAYNAME 

az keyvault secret show \
    --vault-name $AZURE_AKV_NAME \
    --name  AZURESPARMNAME 

az keyvault secret show \
    --vault-name $AZURE_AKV_NAME \
    --name  AZURESPARMPASS 

az keyvault secret show \
    --vault-name $AZURE_AKV_NAME \
    --name  AZURESPARMTENANT

echo 'done'
