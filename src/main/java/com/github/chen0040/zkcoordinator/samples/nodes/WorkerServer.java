package com.github.chen0040.zkcoordinator.samples.nodes;


import com.github.chen0040.zkcoordinator.models.ZkConfig;
import com.github.chen0040.zkcoordinator.nodes.WorkerNode;
import com.github.chen0040.zkcoordinator.utils.IpTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static spark.Spark.get;
import static spark.Spark.port;


/**
 * Created by xschen on 1/7/2017.
 */
public class WorkerServer extends WorkerNode {

   private static final Logger logger = LoggerFactory.getLogger(WorkerServer.class);

   public WorkerServer(ZkConfig zkConfig) {
      super(zkConfig);
   }


   @Override public void startSystem(String ipAddress, int port, String masterId){
      logger.info("start system at {}:{} with id = {}", ipAddress, port, masterId);
   }

   @Override public void stopSystem() {
      logger.info("system shutdown");
   }

   public static void main(String[] args) throws IOException, InterruptedException {
      ZkConfig config = new ZkConfig();
      config.setZkConnect("192.168.10.12:2181,192.168.10.13:2181,192.168.10.14:2181");
      int startingPort = 9000;
      config.setStartingPort(startingPort); // worker node java program will find an un-used port from the port range starting at 9000

      final WorkerServer application = new WorkerServer(config);
      application.addShutdownHook();
      application.start();

      port(IpTools.getNextAvailablePort(startingPort));
      get("/hello", (req, res) -> "Hello World");
   }

}
