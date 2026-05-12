package br.edu.cesmac.odontaval;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OdontavalApplication {

	public static void main(String[] args) {
		SpringApplication.run(OdontavalApplication.class, args);
	}

}
