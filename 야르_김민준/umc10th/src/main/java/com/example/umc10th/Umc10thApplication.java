package com.example.umc10th;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //@EntityListeners를 정상적으로 이용하기 위해 등록
public class Umc10thApplication {

	public static void main(String[] args) {
		SpringApplication.run(Umc10thApplication.class, args);
	}

}
