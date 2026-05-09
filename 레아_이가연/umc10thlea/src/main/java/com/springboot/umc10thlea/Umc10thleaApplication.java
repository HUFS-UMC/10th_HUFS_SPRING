package com.springboot.umc10thlea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Umc10thleaApplication {

    public static void main(String[] args) {
            SpringApplication.run(Umc10thleaApplication.class, args);
    }

}
