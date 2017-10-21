package com.github.chen0040.zookeeper.clients;


import com.github.chen0040.zkcoordinator.models.NodeUri;
import com.github.chen0040.zkcoordinator.models.ZkConfig;
import com.github.chen0040.zkcoordinator.nodes.RequestNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static spark.Spark.get;
import static spark.Spark.port;

/**
 * Created by xschen on 1/7/2017.
 */
public class ProducerNodeServer extends RequestNode {

   private static final Logger logger = LoggerFactory.getLogger(ProducerNodeServer.class);
   final Random random = new Random();

   public ProducerNodeServer(ZkConfig zkConfig) {
      super(zkConfig);
   }


   @Override public void startSystem(String ipAddress, int port, String masterId){
      logger.info("start system at {}:{} with id = {}", ipAddress, port, masterId);

      port(port);

      get("/hello", (req, res) -> {

         List<NodeUri> masters = this.getMasters();
         if(masters.isEmpty()) {
            return "No master to handle the hello-task";
         }
         NodeUri anyMaster = masters.get(random.nextInt(masters.size()));
         int masterPort = anyMaster.getPort();
         String masterHost = anyMaster.getHost();
         final String masterWebApiUrl = "http://" + masterHost + ":" + masterPort + "/hello";

         StringBuilder sb = new StringBuilder();
         sb.append("This is the producer node at " + this.getIpAddress() + ":" + this.getRegisteredPort() + " currently querying master " + masterWebApiUrl);
         sb.append("<br />");
         sb.append(HttpClient.get(masterWebApiUrl));
         return sb.toString();
      });
   }

   @Override public void stopSystem() {
      logger.info("system shutdown");
   }

   public static void main(String[] args) throws IOException, InterruptedException {
      ZkConfig config = new ZkConfig();
      config.setZkConnect("localhost:2181");
      int startingPort = 7000;
      config.setStartingPort(startingPort); // request node java program will find an un-used port from the port range starting at 7000

      final ProducerNodeServer application = new ProducerNodeServer(config);
      application.addShutdownHook();
      application.start();
   }

}
