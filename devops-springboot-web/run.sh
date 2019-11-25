#!/bin/bash

# Executes this Spring Boot web or console app per command-line args.
# See build.sh which packages @SpringBootApplication-specific jar files.
# See run-container.sh to execute the web Docker container locally.
# Chris Joakim, 2019/11/05

arg_count=$#

display_help() {
    echo "script options:"
    echo "  ./run.sh web"
    echo "  ./run.sh noop"
    echo "  ./run.sh cosmos-sql --delete-airports"
    echo "  ./run.sh cosmos-sql --load-airports"
    echo "  ./run.sh cosmos-sql --find-by-pk BDL"
    echo "  ./run.sh cosmos-sql --find-id-and-by-pk af5559e7-360f-4823-853e-3f88ae4a005f UAK"
    echo "  ./run.sh cosmos-sql --query-coll-by-pk airports CLT"
    echo "  ./run.sh cosmos-sql --query-by-location airports -80.842840 35.499581 40000"
    echo ""
}

if [ $arg_count -lt 1 ]
then
    echo "no command-line args specified"
    display_help
    exit
fi

if [ $1 == "help" ] 
then
    display_help
fi

if [ $1 == "web" ] 
then
    echo "executing web app..."
    ./build.sh package_web
    java -Xmx200m -Dspring.profiles.active=web -Dserver.port=8080 -jar target/springboot-airports-web-0.0.1.jar
fi

if [ $1 == "noop" ] 
then
    echo "executing noop app..."
    ./build.sh package_noop
    java -Xmx200m -Dspring.profiles.active=noop -Dserver.port=8080 -jar target/springboot-airports-noop-0.0.1.jar
fi

if [ $1 == "cosmossql" ] 
then
    echo "executing cosmossql app..."
    ./build.sh package_cosmossql
    java -Xmx200m -Dspring.profiles.active=cosmossql -Dserver.port=8080 -jar target/springboot-airports-cosmossql-0.0.1.jar $2 $3 $4 $5 $6 $7 $8 $9
fi
