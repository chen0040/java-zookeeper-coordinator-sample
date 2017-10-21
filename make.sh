#!/usr/bin/env bash

mvn -f pom.xml clean package -U

cp master-node/target/master-node.jar bin/master-node.jar
cp worker-node/target/worker-node.jar bin/worker-node.jar
cp producer-node/target/producer-node.jar bin/producer-node.jar
cp client-application/target/client-application.jar bin/client-application.jar
