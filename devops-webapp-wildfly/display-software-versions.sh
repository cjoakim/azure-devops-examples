#!/bin/bash

# Display the versions of various tools on the DevOps build host.
# Chris Joakim, Microsoft, 2019/11/25

echo '==='
echo 'java -version'
java -version

echo '==='
echo 'mvn --version'
mvn --version

echo '==='
echo 'python --version'
python --version

echo '==='
echo 'pip --version'
pip --version

echo '==='
echo 'python3 --version'
python3 --version

echo '==='
echo 'pip3 --version'
pip3 --version

echo '==='
echo 'node --version'
node --version

echo '==='
echo 'npm --version'
npm --version 

echo '==='
echo 'done'
