package com.github.chen0040.zkcoordinator.samples.controllers;


import com.github.chen0040.zkcoordinator.models.NodeUri;
import com.github.chen0040.zkcoordinator.nodes.ClientNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;


/**
 * Created by xschen on 1/7/2017.
 */
@RestController
public class RequestController {
   @Autowired
   private ClientNode node;

   @Autowired
   private RestTemplate restTemplate;

   private Random random = new Random();

   @RequestMapping(value = "/requests/hello", method = RequestMethod.GET)
   public @ResponseBody String hello(){
      List<NodeUri> producers = node.getProducers();

      if(producers.isEmpty()){
         return "No Producer Node Available!";
      }

      NodeUri node = producers.get(random.nextInt(producers.size()));

      String url = "http://" + node.getHost() + ":" + node.getPort() + "/hello";

      ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

      StringBuilder sb = new StringBuilder();
      sb.append("client-application is trying to invoke a producer at " + url);
      sb.append("<br />");
      sb.append(response.getBody());
      return sb.toString();
   }
}
