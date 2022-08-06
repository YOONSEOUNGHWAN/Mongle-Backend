package com.rtsj.return_to_soju;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReturnToSojuApplication {


//	static {
//		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
//	}

	public static void main(String[] args) {
		SpringApplication.run(ReturnToSojuApplication.class, args);
	}

}
