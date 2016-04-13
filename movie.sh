#!/bin/bash
git pull
mvn clean
mvn package
nohup java -XX:ErrorFile=./java_error_%p.log -Xms500m -Xmx500m -jar ./target/movie-0.2.jar &
