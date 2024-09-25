package com.ls.in.document.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync

/**
 * 	•	설정된 ThreadPoolTaskExecutor가 스레드를 관리하며, 비동기 작업이 발생하면 스레드 풀에서 스레드를 할당합니다.
 * 	•	CorePoolSize는 스레드 풀에서 기본적으로 유지하는 스레드 수(5개).
 * 	•	MaxPoolSize는 필요할 경우 최대 허용되는 스레드 수(10개).
 * 	•	QueueCapacity는 스레드가 부족할 때 작업이 대기하는 큐의 크기(25개).
 */
public class AsyncConfig {
	@Bean(name = "taskExecutor")
	public Executor asyncExecutor(){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(25); // 큐 크기
		executor.setThreadNamePrefix("Async-");
		executor.initialize();
		return executor;
	}
}
