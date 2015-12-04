#!/bin/bash
git pull
mvn clean
mvn package
nohup java -jar ./target/movie-0.2.jar -XX:ErrorFile=./hs_error%p.log &
