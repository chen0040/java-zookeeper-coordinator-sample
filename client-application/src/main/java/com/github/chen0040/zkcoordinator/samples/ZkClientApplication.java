package com.github.chen0040.zkcoordinator.samples;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Created by xschen on 1/7/2017.
 */
@SpringBootApplication
@EnableScheduling
public class ZkClientApplication {

   public static void main(String[] args) {
      SpringApplication.run(ZkClientApplication.class, args);
   }


}
