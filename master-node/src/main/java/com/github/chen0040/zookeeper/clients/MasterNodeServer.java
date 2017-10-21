package com.github.chen0040.zookeeper.clients;


import com.github.chen0040.zkcoordinator.models.ZkConfig;
import com.github.chen0040.zkcoordinator.nodes.MasterNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.io.IOException;
import java.util.function.Consumer;


/**
 * Created by xschen on 1/7/2017.
 */
public class MasterNodeServer extends MasterNode {

   private static final Logger logger = LoggerFactory.getLogger(MasterNodeServer.class);

   public MasterNodeServer(ZkConfig zkConfig) {
      super(zkConfig);
   }

   @Override public void takeLeadership(String ipAddress, int port, String masterId) {
      logger.info("This instance (id = {}) has become leader at {}:{}", masterId, ipAddress, port);
   }

   @Override public void resignLeadership(String ipAddress, int port, String masterId) {
      logger.info("This instance (id = {}) has resigned from leadership at {}:{}", masterId, ipAddress, port);
   }

   @Override public void startSystem(String ipAddress, int port, String masterId){
      logger.info("start system at {}:{} with id = {}", ipAddress, port, masterId);
      Spark.port(port);

      final Consumer<String> assignJob = (task_name) -> {
         this.isTaskAssigned(task_name, (task_name_2, assigned) -> {
            logger.info("is {} assigned? {}", task_name_2, assigned);
            logger.info("additional info: {}", task_name_2);
            if(assigned) {
               this.getWorkerAssigned2Task(task_name, worker_node -> {
                  logger.info("task already assigned to worker node: {}", worker_node);
               });
            } else {
               this.assignTask(task_name, (task_name_3, worker_node) -> {
                  logger.info("task is now assigned to worker node: {}", worker_node);

               });
            }
         });
      };

      Spark.get("/hello", (req, res) -> {
         logger.info("hello get invoked.");
         new Thread(() -> {
            this.taskExists("hello-task", (task_name, exists) -> {
               logger.info("is hello-task exists? {}", exists);
               logger.info("additional info: {}", task_name);
               if(exists) {
                  assignJob.accept("hello-task");
               } else {
                  this.createTask("hello-task", (task_name_2) -> {
                     logger.info("task created: {}", task_name_2);
                     assignJob.accept("hello-task");
                  });
               }
            });
         }).start();


         return "This is the master node at " + this.getIpAddress() + ":" + this.getRegisteredPort() + " which is currently dispatching task to worker nodes";
      });
   }

   @Override public void stopSystem() {
      logger.info("system shutdown");
   }

   public static void main(String[] args) throws IOException, InterruptedException {
      ZkConfig config = new ZkConfig();
      config.setZkConnect("localhost:2181");

      int startingPort = 6000;
      config.setStartingPort(startingPort); // master node java program will find an un-used port from the port range starting at 6000

      final MasterNodeServer application = new MasterNodeServer(config);
      application.addShutdownHook();
      application.start();





   }

}
