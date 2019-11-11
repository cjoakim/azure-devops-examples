# devops-arm-1

An example of deploying Azure ARM templates in Azure DevOps,
using Azure KeyVault.

## Links

- https://docs.microsoft.com/en-us/azure/azure-resource-manager/vs-resource-groups-project-devops-pipelines

- https://docs.microsoft.com/en-us/azure/azure-resource-manager/resource-manager-tutorial-use-azure-pipelines

- https://docs.microsoft.com/en-us/cli/azure/create-an-azure-service-principal-azure-cli?view=azure-cli-latest

---

## Main Files

- [create-service-principal.sh](create-service-principal.sh)
- [akv-secrets.sh](akv-secrets.sh)
- [storage-template.json](storage-template.json)
- [storage-parameters.json](storage-parameters.json)
- [azure-pipelines.yml](azure-pipelines.yml)

---

## Screen Shots

### List of Variable Groups 

![variable-groups-list](img/variable-groups-list.png)

---

### Regular Variable Group

![regular-variable-group](img/regular-variable-group.png)

---

### Key Vault Variable Group

![key-vault-variable-group](img/key-vault-variable-group.png)

---

### Successful Pipeline Build

![successful-pipeline-build](img/successful-pipeline-build.png)

---

### Deployed Resources in Azure Portal

![azure-portal-deployed-resources](img/azure-portal-deployed-resources.png)

---