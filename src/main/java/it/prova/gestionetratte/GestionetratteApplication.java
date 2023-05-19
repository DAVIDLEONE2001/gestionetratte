package it.prova.gestionetratte;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.service.AirbusService;

@SpringBootApplication
public class GestionetratteApplication implements CommandLineRunner  {

	@Autowired
	AirbusService airbusService;
	
	public static void main(String[] args)  {
		SpringApplication.run(GestionetratteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		System.err.println("prova");
//		
//		Airbus a1 = new Airbus("A1","Primo",LocalDate.now(),120);
//		airbusService.inserisciNuovo(a1);
	}

}
