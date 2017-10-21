package com.github.chen0040.zkcoordinator.samples.configs;


import com.github.chen0040.zkcoordinator.models.ZkConfig;
import com.github.chen0040.zkcoordinator.nodes.ClientNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


/**
 * Created by xschen on 1/7/2017.
 */
@Configuration
public class ClientNodeConfig {

   @Value("${app.zk-connect}")
   private String zkConnect;

   @Bean
   public ClientNode clientNode() throws IOException {
      ZkConfig zkConfig = new ZkConfig();
      zkConfig.setZkConnect(zkConnect);
      ClientNode clientNode = new ClientNode(zkConfig);
      clientNode.start();
      return clientNode;
   }

   @Bean
   public RestTemplate restTemplate() {
      return new RestTemplate();
   }
}
