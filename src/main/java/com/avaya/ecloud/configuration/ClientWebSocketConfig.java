package com.avaya.ecloud.configuration;

import com.avaya.ecloud.network.ClientWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;


@Configuration
public class ClientWebSocketConfig {

   @Bean
   public WebSocketClient standardWebSocketClient() {
       return new StandardWebSocketClient();
   }

   @Bean
   public WebSocketHandler webSocketHandler() {
       return new ClientWebSocketHandler();
   }
}