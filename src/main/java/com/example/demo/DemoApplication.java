package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.function.RouterFunctions;

import java.io.IOException;

@SpringBootApplication
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        // Access environment variables directly
        String dbUrl = System.getenv("DB_URL");
        String dbUsername = System.getenv("DB_USERNAME");
        String dbPassword = System.getenv("DB_PASSWORD");

        // Set the properties as system properties if needed
        System.setProperty("DB_URL", dbUrl);
        System.setProperty("DB_USERNAME", dbUsername);
        System.setProperty("DB_PASSWORD", dbPassword);

        SpringApplication.run(DemoApplication.class, args);
    }

    // Serve React index.html for all unspecified routes
    @GetMapping(value = {"/", "/{path:^(?!api|static).*}"})
    public String serveReactApp() {
        return "forward:/build/index.html";
    }

    @Bean
    public RouterFunction<ServerResponse> staticResourceRouter() throws IOException {
        // Serve static resources from the /build folder
        return RouterFunctions.resources("/build/**", new ClassPathResource("static/build/"));
    }
}
