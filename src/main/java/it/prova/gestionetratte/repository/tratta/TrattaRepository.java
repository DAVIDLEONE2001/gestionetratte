package it.prova.gestionetratte.repository.tratta;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.model.Tratta;

public interface TrattaRepository extends CrudRepository<Tratta, Long>, TrattaRepositoryCustom {

	@Query("select f from Tratta f join fetch f.airbus")
	List<Tratta> findAllTratteEager();
	
	@Query("from Tratta f join fetch f.airbus where f.id = ?1")
	Tratta findSingleTrattaEager(Long id);
	
}
