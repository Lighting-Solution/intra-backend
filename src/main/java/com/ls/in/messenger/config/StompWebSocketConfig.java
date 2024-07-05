package com.ls.in.messenger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Controller
@EnableWebSocketMessageBroker
@Configuration
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {


	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/pub"); // 클라이언트가 보내는 메시지의 prefix
		registry.enableSimpleBroker("/sub"); // 클라이언트가 구독할 수 있는 prefix
	}


	//endpoint를 /stomp로 하고, allowedOrigins를 "*"로 하면 페이지에서
	//Get /info 404 Error가 발생한다. 그래서 아래와 같이 2개의 계층으로 분리하고
	//origins를 개발 도메인으로 변경하니 잘 동작하였다.
	//이유는 왜 그런지 아직 찾지 못함
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/stomp/chat") // 클라이언트가 연결할 수 있는 엔드포인트
				.setAllowedOrigins("http://localhost:3000") // React 개발 서버의 주소
				.withSockJS();
	}
}