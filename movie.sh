#!/bin/bash
git pull
mvn clean
mvn compiler:compile
mvn resources:resources
mvn package
nohup java -jar ./target/movie-0.1.jar &
