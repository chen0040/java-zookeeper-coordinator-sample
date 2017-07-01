package com.github.chen0040.zkcoordinator.samples;


import com.github.chen0040.zkcoordinator.models.ZkConfig;
import com.github.chen0040.zkcoordinator.nodes.RequestNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * Created by xschen on 1/7/2017.
 */
public class RequestServer extends RequestNode {

   private static final Logger logger = LoggerFactory.getLogger(RequestServer.class);

   public RequestServer(ZkConfig zkConfig) {
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
      config.setStartingPort(7000); // request node java program will find an un-used port from the port range starting at 7000

      final RequestServer application = new RequestServer(config);
      application.addShutdownHook();
      application.runForever();
   }

}
