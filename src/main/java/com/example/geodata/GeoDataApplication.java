package com.example.geodata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;

import java.util.concurrent.Executors;

@SpringBootApplication
@Slf4j
public class GeoDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeoDataApplication.class, args);
    }

    @Bean
    public AsyncTaskExecutor applicationTaskExecutor() {
        return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
    }

    @Bean
    public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
        return protocolHandler -> {
            log.info("Configuring " + protocolHandler + " to use VirtualThreadPerTaskExecutor");
            protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        };
    }

}
