package it.prova.gestionetratte.repository.airbus;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionetratte.model.Airbus;

public interface AirbusRepository extends CrudRepository<Airbus, Long>, AirbusRepositoryCustom {

	@Query("select distinct r from Airbus r left join fetch r.tratte " )
	List<Airbus> findAllEager();

	@Query("from Airbus r left join fetch r.tratte where r.id=?1")
	Airbus findByIdEager(Long idAirbus);
	
	@Query(value = "select * from airbus a where a.id in (\r\n"
			+ " select t1.airbus_id \r\n"
			+ " from Tratta t1, Tratta t2 \r\n"
			+ "     where t1.airbus_id = t2.airbus_id\r\n"
			+ " and t1.id <> t2.id \r\n"
			+ " and t1.data = t2.data \r\n"
			+ " and (t1.oradecollo between t2.oradecollo and t2.oraatterraggio or t1.oraatterraggio between t2.oradecollo and t2.oraatterraggio)\r\n"
			+ " )", nativeQuery = true)
	List<Airbus> airbusConSovrapposizioni();

}
