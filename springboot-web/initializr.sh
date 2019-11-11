#!/bin/bash

# Initialize Spring Boot projects with the 'spring init' command.
# Chris Joakim, 2019/11/02

name=airports
target_dir=$name
web_dependencies=web,jpa,actuator,thymeleaf,azure-support
batch_dependencies=batch,jpa,actuator,azure-support
console_dependencies=jpa,actuator,azure-support
dependencies=$web_dependencies

rm -rf $name
mkdir $name/

spring init \
    --name=$name \
    --dependencies=$dependencies \
    --groupId=com.chrisjoakim.azure \
    --artifactId=$name \
    --java-version=1.8 \
    $name/$name.zip

cd $name/
unzip $name.zip
cd ..

echo 'done'
