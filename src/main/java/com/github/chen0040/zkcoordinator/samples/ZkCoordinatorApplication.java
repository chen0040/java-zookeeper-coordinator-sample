package com.github.chen0040.zkcoordinator.samples;


import com.github.chen0040.zkcoordinator.models.ZkConfig;
import com.github.chen0040.zkcoordinator.nodes.ClientNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;


/**
 * Created by xschen on 1/7/2017.
 */
@SpringBootApplication
@EnableScheduling
public class ZkCoordinatorApplication {

   public static void main(String[] args) {
      SpringApplication.run(ZkCoordinatorApplication.class, args);
   }


}
