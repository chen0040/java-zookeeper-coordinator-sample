# java-zookeeper-coordinator-sample

Sample distributed system application that makes use of java-zookeeper-coordinator

The cluster is built using the java-zookeeper-coordinator ([https://github.com/chen0040/java-zookeeper-coordinator](https://github.com/chen0040/java-zookeeper-coordinator)).
 
The cluster consists of the following nodes (standalone micro services connected via zookeeper):

* master-node: one or more master which supports HA and fail-safe
* worker-node: one or more work-node which supports HA and job partitioning
* producer-node: one or more producer node which supports HA which servers as the sprout of the cluster that takes in input data.
* client-application: the client application which sends input data to the producer node for processing.

The client application runs at localhost:8080. 

The workflow of the cluster is described below:

1. user enter some data into the web interface of the client-application at localhost:8080. 
2. the client-application sends the data to one of the producer node in the cluster (via the client-side load balancer provided by java-zookeeper-coordinator)
3. the producer node that receives the data sends it to one of the master nodes in the cluster (based on the hashed key of the data piece)
4. the master node then routes the data to one of the work nodes that has been assigned to handle the job associated that the hashed key of the data piece

# Usage

To run locally, start the zookeeper-standalone server which runs the zookeeper clientPort at localhost:2181 and the web api port at localhost:4181

```bash
java -jar third-party/zookeeper-standalone.jar
```

Run the make.ps1 (Windows) or make.sh (Linux or Mac) to build the jars in the bin folder, then run the following command to start the cluster:

```bash
java -jar bin/master-node.jar
java -jar bin/master-node.jar
java -jar bin/producer-node.jar
java -jar bin/producer-node.jar
java -jar bin/worker-node.jar
java -jar bin/worker-node.jar
java -jar bin/client-application.jar
```

The above commands start two master nodes, two producer nodes and two worker nodes, as well as one client-application

Now navigate to localhost:8080 to interact with the client-application.



