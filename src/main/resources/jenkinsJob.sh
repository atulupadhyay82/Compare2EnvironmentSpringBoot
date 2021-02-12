#!/bin/bash

sudo su
chmod +x gradlew
./gradlew build
pkill -f "staging"
echo "This is start $(pwd)"
cd $WORKSPACE/src/main/resources/stagingJarFile/NewJar
echo "This is $(pwd)"
nohup java -Xmx22g -jar staging1075N1071OnDev.jar>log.log 2>&1  &
cd ../OldJar
nohup java -Xmx22g -jar staging1075OnDev.jar>log.log 2>&1 &
./gradlew run
