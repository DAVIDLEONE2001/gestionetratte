package it.prova.gestionetratte.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionetratte.dto.AirbusDTOSovrapp;
import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.repository.airbus.AirbusRepository;
import it.prova.gestionetratte.repository.tratta.TrattaRepository;

@Service
@Transactional(readOnly = true)
public class AirbusServiceImpl implements AirbusService {

	@Autowired
	private AirbusRepository repository;
	@Autowired
	private TrattaRepository trattaRepository;

	@Override
	public List<Airbus> listAllElements() {

		return (List<Airbus>) repository.findAll();

	}

	@Override
	public List<Airbus> listAllElementsEager() {
		return (List<Airbus>) repository.findAllEager();
	}

	@Override
	public Airbus caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Airbus caricaSingoloElementoConTratte(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Airbus aggiorna(Airbus airbusInstance) {
		return repository.save(airbusInstance);
	}

	@Override
	@Transactional
	public Airbus inserisciNuovo(Airbus airbusInstance) {
		return repository.save(airbusInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Long idToRemove) {
		repository.deleteById(idToRemove);
	}

	@Override
	public List<Airbus> findByExample(Airbus example) {
		return repository.findByExample(example);
	}

	@Override
	public Set<AirbusDTOSovrapp> listaAirbusDTOSovrapp() {
		List<Tratta> tratte = (List<Tratta>) trattaRepository.findAll();
		Set<AirbusDTOSovrapp> airbusDTOSovrapp = new HashSet<>();
		for (int i = 0; i < tratte.size()-1; i++) {
			AirbusDTOSovrapp airTemp = AirbusDTOSovrapp.buildAirbusDTOFromModel(tratte.get(i).getAirbus(), false);
			if (tratte.get(i).getOraDecollo().isAfter(tratte.get(i + 1).getOraDecollo())
					|| tratte.get(i).getOraAtterraggio().isAfter(tratte.get(i + 1).getOraAtterraggio())) {
				airTemp.setConSovrapposizioni(true);
			}
			airbusDTOSovrapp.add(airTemp);
		}
		return airbusDTOSovrapp;
	}

}
