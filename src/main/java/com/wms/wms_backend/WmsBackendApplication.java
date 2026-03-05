package com.wms.wms_backend;

import com.wms.wms_backend.service.WarrantService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WmsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WmsBackendApplication.class, args);
    }

    // This Bean runs automatically right after the application starts
    @Bean
    CommandLineRunner run(WarrantService warrantService) {
        return args -> {
            warrantService.createMockCourtData();
        };
    }
}