package it.prova.gestionetratte.repository.airbus;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionetratte.model.Airbus;

public interface AirbusRepository extends CrudRepository<Airbus, Long> {

	@Query("select distinct r from Airbus r left join fetch r.tratte ")
	List<Airbus> findAllEager();

	@Query("from Airbus r left join fetch r.tratte where r.id=?1")
	Airbus findByIdEager(Long idAirbus);

}
