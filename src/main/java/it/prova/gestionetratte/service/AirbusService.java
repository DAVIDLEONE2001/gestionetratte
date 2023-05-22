package it.prova.gestionetratte.service;

import java.util.List;
import java.util.Set;

import it.prova.gestionetratte.dto.AirbusDTOSovrapp;
import it.prova.gestionetratte.model.Airbus;

public interface AirbusService {

	List<Airbus> listAllElements();

	List<Airbus> listAllElementsEager();

	Airbus caricaSingoloElemento(Long id);

	Airbus caricaSingoloElementoConTratte(Long id);

	Airbus aggiorna(Airbus airbusInstance);

	Airbus inserisciNuovo(Airbus airbusInstance);

	void rimuovi(Long idToRemove);

	List<Airbus> findByExample(Airbus example);

	Set<AirbusDTOSovrapp> listaAirbusDTOSovrapp();

}
