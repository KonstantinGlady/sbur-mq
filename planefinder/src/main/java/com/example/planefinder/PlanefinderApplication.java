package com.example.planefinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management*/
@SpringBootApplication
public class PlanefinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanefinderApplication.class, args);
    }

}
