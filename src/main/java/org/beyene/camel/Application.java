package org.beyene.camel;

import org.apache.camel.impl.DefaultShutdownStrategy;
import org.apache.camel.spi.ShutdownStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Bean
    public ShutdownStrategy shutdownStrategy() {
        DefaultShutdownStrategy strategy = new DefaultShutdownStrategy();
        strategy.setTimeout(15);
        return strategy;
    }
}
