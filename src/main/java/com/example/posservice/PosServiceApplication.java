package com.example.posservice;

import com.example.posservice.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class PosServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PosServiceApplication.class, args);
    }


}
