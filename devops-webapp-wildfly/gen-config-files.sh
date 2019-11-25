#!/bin/bash

# Generate Wildfly or any other configuration files using
# secrets in an Azure DevOps Pipeline. 
# Chris Joakim, 2018/11/25

echo 'deleting previous venv...'
rm -rf bin/
rm -rf lib/
rm -rf include/
rm -rf man/

echo 'creating new venv ...'
python3 -m venv .
source bin/activate

echo 'installing/upgrading libs ...'
pip3 install --upgrade pip-tools
pip3 install --upgrade docopt
pip3 install --upgrade Jinja2

echo 'pip freeze ...'
pip3 freeze > requirements.txt

echo 'cat requirements.txt ...'
cat requirements.txt

echo 'executing gen-config-files.py ...'
python3 gen-config-files.py $1 $2 $3 $4 $5 $6 $7 $8 $9

echo 'done'


# ./gen-config-files.sh sample_xml $AZURE_ACR_USER_NAME $AZURE_ACR_USER_PASS
