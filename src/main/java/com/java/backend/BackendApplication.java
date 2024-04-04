package com.java.backend;

import com.java.backend.config.CorsConfig;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
//@Import(CorsConfig.class) // Import the CorsConfig class
@EntityScan(basePackages = {"com.java.backend.entity"})
public class BackendApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		System.out.println("Application is running ");
	}

}
