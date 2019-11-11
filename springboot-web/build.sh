#!/bin/bash

# Spring Boot application build script.  Note that there are several
# @SpringBootApplication classes in this project, therefore there are
# several Maven POM files with specify their <mainClass> and Spring Profile.
# Chris Joakim, 2019/11/05

arg_count=$#
skip_tests=true

mvn_clean_compile() {
    echo "mvn clean compile function"
    mvn clean compile
}

mvn_package_web_app() {
    create_build_resources
    echo "building web app with pom_web.xml"
    mvn -f pom_web.xml -Dmaven.test.skip=$skip_tests clean package
    echo "then run: java -Xmx200m -Dspring.profiles.active=web -jar target/airports-web-0.0.1.jar"
}

mvn_package_noop_app() {
    create_build_resources
    echo "building web app with pom_noop.xml"
    mvn -f pom_noop.xml -Dmaven.test.skip=$skip_tests clean package
    echo "then run: java -Xmx200m -Dspring.profiles.active=noop -jar target/airports-noop-0.0.1.jar"
}

mvn_package_cosmossql_app() {
    create_build_resources
    echo "building web app with pom_cosmossql.xml"
    mvn -f pom_cosmossql.xml -Dmaven.test.skip=$skip_tests clean package
    echo "then run: java -Xmx200m -Dspring.profiles.active=cosmossql -jar target/airports-cosmossql-0.0.1.jar --find-by-pk BDL"
}

create_build_resources() {
    date -u > src/main/resources/build_date.txt
    whoami  > src/main/resources/build_user.txt
}

display_help() {
    echo "script options:"
    echo "  ./build.sh compile"
    echo "  ./build.sh package_web"
    echo "  ./build.sh package_noop"
    echo "  ./build.sh package_cosmossql"
    echo "  ./build.sh package_all"
    echo "  ./build.sh container_web"
    echo "  ./build.sh mvn_tree"
    echo "  ./build.sh mvn_classpath"
}


if [ $arg_count -gt 0 ]
then
    if [ $1 == "help" ] 
    then
        display_help
    fi

    if [ $1 == "compile" ] 
    then
        mvn_clean_compile
        # echo "mvn clean compile"
        # mvn clean compile
    fi

    if [ $1 == "package_web" ]
    then
        mvn_package_web_app  
    fi

    if [ $1 == "package_noop" ]
    then
        mvn_package_noop_app
    fi

    if [ $1 == "package_cosmossql" ]
    then
        mvn_package_cosmossql_app
    fi

    if [ $1 == "package_all" ]
    then
        mvn_package_web_app
        mvn_package_noop_app
        mvn_package_cosmossql_app
    fi

    if [ $1 == "container_web" ]
    then
        mvn_package_web_app
        echo "docker build -t cjoakim/azure-springboot-airports-web ."
        docker build -t cjoakim/azure-springboot-airports-web .
    fi

    if [ $1 == "mvn_tree" ]
    then
        echo "mvn dependency:tree"
        mvn dependency:tree
    fi

    if [ $1 == "mvn_classpath" ]
    then
        echo "mvn dependency:build-classpath"
        mvn dependency:build-classpath > mvn-build-classpath.txt
        cat mvn-build-classpath.txt | tr ":" "\n" > mvn-build-classpath-lines.txt
        cat mvn-build-classpath-lines.txt | grep repository | sort 
        rm mvn-build-classpath*
    fi
else
    display_help
fi

echo 'done'
