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


/**
 * Created by xschen on 1/7/2017.
 */
@RestController
public class RequestController {
   @Autowired
   private ClientNode node;

   @Autowired
   private RestTemplate restTemplate;

   @RequestMapping(value = "/requests/hello", method = RequestMethod.GET)
   public @ResponseBody String hello(){
      List<NodeUri> uris = node.getProducers();

      if(uris.isEmpty()){
         return "No Request Node Available!";
      }

      NodeUri node = uris.get(0);

      String url = node.getHost() + "/hello";

      return url;

      /*
      ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

      return response.getBody();*/
   }
}
