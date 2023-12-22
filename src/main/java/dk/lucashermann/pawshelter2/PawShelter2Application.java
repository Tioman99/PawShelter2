package dk.lucashermann.pawshelter2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"dk.lucashermann.pawshelter2.controllers"})
@ComponentScan(basePackages = {"dk.lucashermann.pawshelter2.services"})
@ComponentScan(basePackages = {"dk.lucashermann.pawshelter2.models"})
@ComponentScan(basePackages = {"dk.lucashermann.pawshelter2.repositories"})
public class PawShelter2Application {

    public static void main(String[] args) {
        SpringApplication.run(PawShelter2Application.class, args);
    }

}
