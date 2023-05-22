package it.prova.gestionetratte.web.api;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.gestionetratte.dto.AirbusDTO;
import it.prova.gestionetratte.dto.AirbusDTOSovrapp;
import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.service.AirbusService;
import it.prova.gestionetratte.web.api.exception.AirbusNotFoundException;
import it.prova.gestionetratte.web.api.exception.IdNotNullForInsertException;

@RestController
@RequestMapping("api/airbus")
public class AirbusController {

	public AirbusController() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private AirbusService airbusService;

	@GetMapping
	public List<AirbusDTO> getAll() {
		// senza DTO qui hibernate dava il problema del N + 1 SELECT
		// (probabilmente dovuto alle librerie che serializzano in JSON)
		return AirbusDTO.createAirbusDTOListFromModelList(airbusService.listAllElementsEager(), true);
	}

	@GetMapping("/{id}")
	public AirbusDTO findById(@PathVariable(value = "id", required = true) long id) {
		Airbus regista = airbusService.caricaSingoloElementoConTratte(id);

		if (regista == null)
			throw new AirbusNotFoundException("Airbus not found con id: " + id);

		return AirbusDTO.buildAirbusDTOFromModel(regista, true);
	}

	// gli errori di validazione vengono mostrati con 400 Bad Request ma
	// elencandoli grazie al ControllerAdvice
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<AirbusDTO> createNew(@Valid @RequestBody AirbusDTO airbusInput) {
		// se mi viene inviato un id jpa lo interpreta come update ed a me (producer)
		// non sta bene
		if (airbusInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");

		Airbus airbusInserito = airbusService.inserisciNuovo(airbusInput.buildAirbusModel());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(AirbusDTO.buildAirbusDTOFromModel(airbusInserito, true));

	}

	@PutMapping("/{id}")
	public AirbusDTO update(@Valid @RequestBody AirbusDTO airbusInput, @PathVariable(required = true) Long id) {
		Airbus regista = airbusService.caricaSingoloElemento(id);

		if (regista == null)
			throw new AirbusNotFoundException("Airbus not found con id: " + id);

		airbusInput.setId(id);
		Airbus registaAggiornato = airbusService.aggiorna(airbusInput.buildAirbusModel());
		return AirbusDTO.buildAirbusDTOFromModel(registaAggiornato, false);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<String> delete(@PathVariable(required = true) Long id) {
		Airbus regista = airbusService.caricaSingoloElemento(id);

		if (regista == null)
			throw new AirbusNotFoundException("Airbus not found con id: " + id);

		airbusService.rimuovi(id);
		return ResponseEntity.ok("Airbus con id=" + id + " eliminato con successo");
	}

	@PostMapping("/search")
	public List<AirbusDTO> search(@RequestBody AirbusDTO example) {
		return AirbusDTO.createAirbusDTOListFromModelList(airbusService.findByExample(example.buildAirbusModel()),
				false);
	}
	
	@GetMapping("/listaAirbusEvidenziandoSovrapposizioni")
	public List<AirbusDTOSovrapp> listaAirbusEvidenziandoSovrapposizioni () {
		
		return  AirbusDTOSovrapp.createAirbusDTOListFromModelList(airbusService.listaAirbusConSovrapp(), false) ;
	}
	
}
