package com.github.chen0040.zookeeper.clients;


import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;


/**
 * Created by xschen on 3/10/16.
 */
public class HttpClient implements Serializable {
   private static final String DATA_ENCODING = "UTF-8";
   private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);
   private static final long serialVersionUID = 4661645115933875389L;


   private static CloseableHttpClient buildClient() {
      //HttpClientBuilder builder = HttpClientBuilder.create();

      int timeout = 60;
      RequestConfig config = RequestConfig.custom()
              .setSocketTimeout(timeout * 1000)
              .setConnectionRequestTimeout(timeout * 1000)
              .setConnectTimeout(timeout * 1000)
              .build();

      return HttpClients.custom().setDefaultRequestConfig(config).build(); //builder.build();

   }


   public static String get(final String url) {
      String json = "";
      try {
         CloseableHttpClient httpClient = buildClient();
         HttpGet request = new HttpGet(url);
         CloseableHttpResponse response = httpClient.execute(request);
         if (response.getEntity() != null) {
            json = EntityUtils.toString(response.getEntity(), DATA_ENCODING);
         }
      }
      catch (Exception ex2) {
         json = ex2.getMessage();
      }

      return json;
   }
}
