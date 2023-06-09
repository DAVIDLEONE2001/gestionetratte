package it.prova.gestionetratte.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.gestionetratte.model.Airbus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirbusDTOSovrapp  {

	private Long id;
	@NotBlank(message = "{codice.notblank}")
	private String codice;
	@NotBlank(message = "{descrizione.notblank}")
	private String descrizione;
	@NotNull(message = "{data.notblank}")
	private LocalDate dataInizioServizio;
	@NotNull(message = "{numerop.notblank}")
	private Integer numeroPasseggeri;
	
	private Boolean conSovrapposizioni = true;
	
	@JsonIgnoreProperties(value = { "airbus" })
	private Set<TrattaDTO> tratte = new HashSet<TrattaDTO>(0);

	public AirbusDTOSovrapp() {
	}

	public AirbusDTOSovrapp(Long id, @NotBlank(message = "{codice.notblank}") String codice,
			@NotBlank(message = "{descrizione.notblank}") String descrizione,
			@NotBlank(message = "{data.notblank}") LocalDate dataInizioServizio,
			@NotBlank(message = "{numerop.notblank}") Integer numeroPasseggeri) {
		super();
		this.id = id;
		this.codice = codice;
		this.descrizione = descrizione;
		this.dataInizioServizio = dataInizioServizio;
		this.numeroPasseggeri = numeroPasseggeri;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	

	public LocalDate getDataInizioServizio() {
		return dataInizioServizio;
	}

	public void setDataInizioServizio(LocalDate dataInizioServizio) {
		this.dataInizioServizio = dataInizioServizio;
	}

	public Integer getNumeroPasseggeri() {
		return numeroPasseggeri;
	}

	public void setNumeroPasseggeri(Integer numeroPasseggeri) {
		this.numeroPasseggeri = numeroPasseggeri;
	}

	public Set<TrattaDTO> getTratte() {
		return tratte;
	}

	public void setTratte(Set<TrattaDTO> tratte) {
		this.tratte = tratte;
	}

	public Airbus buildAirbusModel() {
		return new Airbus(this.id, this.codice, this.descrizione, this.dataInizioServizio, this.numeroPasseggeri);
	}

	public static AirbusDTOSovrapp buildAirbusDTOFromModel(Airbus airbusModel, boolean includeTratte) {
		AirbusDTOSovrapp result = new AirbusDTOSovrapp(airbusModel.getId(), airbusModel.getCodice(), airbusModel.getDescrizione(),
				airbusModel.getDataInizioServizio(), airbusModel.getNumeroPasseggeri());
		if (includeTratte)
			result.setTratte(TrattaDTO.createTrattaDTOSetFromModelSet(airbusModel.getTratte(), false));
		return result;
	}

	public static List<AirbusDTOSovrapp> createAirbusDTOListFromModelList(List<Airbus> modelListInput, boolean includeTratte) {
		return modelListInput.stream().map(airbusEntity -> {
			AirbusDTOSovrapp result = AirbusDTOSovrapp.buildAirbusDTOFromModel(airbusEntity, includeTratte);
			if (includeTratte)
				result.setTratte(TrattaDTO.createTrattaDTOSetFromModelSet(airbusEntity.getTratte(), false));
			return result;
		}).collect(Collectors.toList());
	}

	public Boolean getConSovrapposizioni() {
		return conSovrapposizioni;
	}

	public void setConSovrapposizioni(Boolean conSovrapposizioni) {
		this.conSovrapposizioni = conSovrapposizioni;
	}

}
