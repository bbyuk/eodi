package com.bb.eodi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class EodiBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(EodiBatchApplication.class, args);
	}

}
