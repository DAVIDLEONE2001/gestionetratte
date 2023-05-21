package it.prova.gestionetratte.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionetratte.model.StatoTratta;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.repository.tratta.TrattaRepository;

@Service
@Transactional(readOnly = true)
public class TrattaServiceImpl implements TrattaService {

	@Autowired
	private TrattaRepository repository;

	@Override
	public List<Tratta> listAllElements(boolean eager) {

		if (eager) {
			return (List<Tratta>) repository.findAllTratteEager();
		}

		return (List<Tratta>) repository.findAll();
	}

	@Override
	public List<Tratta> listAllElementsEager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tratta caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Tratta caricaSingoloElementoConAirbus(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Tratta aggiorna(Tratta trattaInstance) {
		return repository.save(trattaInstance);
	}

	@Override
	@Transactional
	public Tratta inserisciNuovo(Tratta trattaInstance) {
		return repository.save(trattaInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Long idToRemove) {
//TODO
		repository.deleteById(idToRemove);

	}

	@Override
	public List<Tratta> findByExample(Tratta example) {
		return repository.findByExample(example);
	}

	@Override
	public Tratta caricaSingoloElementoEager(Long id) {
		return repository.findSingleTrattaEager(id);
	}

	@Override
	@Transactional
	public List<Tratta> getTratteConcluse() {
		List<Tratta> result = (List<Tratta>) repository.findAll();
		return result.stream().filter(tratta -> tratta.getOraAtterraggio().isBefore(LocalDateTime.now()))
				.peek(tratta -> tratta.setStato(StatoTratta.CONCLUSA)).collect(Collectors.toList());
	}

}
