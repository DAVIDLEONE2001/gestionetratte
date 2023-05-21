package it.prova.gestionetratte;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.model.StatoTratta;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.service.AirbusService;
import it.prova.gestionetratte.service.TrattaService;

@SpringBootApplication
public class GestionetratteApplication implements CommandLineRunner {

	@Autowired
	private AirbusService airbusService;
	@Autowired
	private TrattaService trattaService;

	public static void main(String[] args) {
		SpringApplication.run(GestionetratteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println("prova");
		
		Airbus a1 = new Airbus("A1","Primo",LocalDate.now(),120);
		Tratta t1 = new Tratta("mimmo-milanio","da mimmo fino miolanio",LocalDate.now(),LocalDateTime.now(),LocalDateTime.now(),StatoTratta.ATTIVA,a1);
		a1.getTratte().add(t1);
		airbusService.inserisciNuovo(a1);
		trattaService.inserisciNuovo(t1);
		
		
	}

}
