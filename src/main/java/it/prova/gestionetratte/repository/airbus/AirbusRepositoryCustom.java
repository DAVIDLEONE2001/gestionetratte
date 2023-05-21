package it.prova.gestionetratte.repository.airbus;

import java.util.List;

import it.prova.gestionetratte.model.Airbus;

public interface AirbusRepositoryCustom {

	public List<Airbus> findByExample(Airbus example);
	
}
