#!/bin/bash

# Compile the Java code and package the WAR file for this app.
# Chris Joakim, Microsoft, 2019/11/23

date -u > src/main/resources/build_date.txt
cat src/main/resources/build_date.txt

whoami > src/main/resources/build_user.txt
cat src/main/resources/build_user.txt

echo 'executing mvn package ...'
mvn clean package

echo 'listing contents of target/root.war ...'
jar tvf target/root.war

echo 'copying war file to docker-dir/deployments directory...'
cp target/root.war docker-dir/deployments

echo 'listing contents of docker-dir/deployments ...'
ls -al docker-dir/deployments
