#!/bin/bash

# Create an Azure Service Principal with the Azure CLI.
# Chris Joakim, Microsoft, 2019/11/11

# az login   <-- run this first

az ad sp create-for-rbac --name cjoakimarm1

az ad sp list --all

# One can login with the Service Principal as follows:
# az login --service-principal --username APP_ID --password PASSWORD --tenant TENANT_ID
