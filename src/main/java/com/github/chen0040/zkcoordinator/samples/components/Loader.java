package com.github.chen0040.zkcoordinator.samples.components;


import com.github.chen0040.zkcoordinator.nodes.ClientNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Created by xschen on 1/7/2017.
 */
@Component
public class Loader implements ApplicationListener<ApplicationReadyEvent> {



   @Override public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

      ApplicationContext context = applicationReadyEvent.getApplicationContext();
      ClientNode clientNode = context.getBean(ClientNode.class);
      try {
         clientNode.start();
      }
      catch (IOException e) {
         e.printStackTrace();
      }
   }
}
