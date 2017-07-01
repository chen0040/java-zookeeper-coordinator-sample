package com.github.chen0040.zkcoordinator.samples.nodes;


import com.github.chen0040.zkcoordinator.models.ZkConfig;
import com.github.chen0040.zkcoordinator.nodes.MasterNode;
import com.github.chen0040.zkcoordinator.utils.IpTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static spark.Spark.get;
import static spark.Spark.port;


/**
 * Created by xschen on 1/7/2017.
 */
public class MasterServer extends MasterNode {

   private static final Logger logger = LoggerFactory.getLogger(MasterServer.class);

   public MasterServer(ZkConfig zkConfig) {
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
      port(port);
   }

   @Override public void stopSystem() {
      logger.info("system shutdown");
   }

   public static void main(String[] args) throws IOException, InterruptedException {
      ZkConfig config = new ZkConfig();
      config.setZkConnect("192.168.10.12:2181,192.168.10.13:2181,192.168.10.14:2181");

      int startingPort = 6000;
      config.setStartingPort(startingPort); // master node java program will find an un-used port from the port range starting at 6000

      final MasterServer application = new MasterServer(config);
      application.addShutdownHook();
      application.start();


      get("/hello", (req, res) -> "Hello World");

   }

}
