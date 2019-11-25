#!/bin/bash

mvn archetype:generate \
    -DgroupId=com.chrisjoakim.azure \
    -DartifactId=webapp \
    -Dversion=1.0-SNAPSHOT \
    -DarchetypeGroupId=org.wildfly.archetype \
    -DarchetypeArtifactId=wildfly-jakartaee-webapp-archetype \
    -DarchetypeVersion=18.0.0.Final
