package dk.lucashermann.pawshelter2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/users/**") // Define the paths where CORS is enabled
                .allowedOrigins("http://127.0.0.1:3000") // Specify the allowed origin(s)
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // Specify the allowed methods
    }
}
