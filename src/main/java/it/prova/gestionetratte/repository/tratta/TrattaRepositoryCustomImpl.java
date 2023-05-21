package it.prova.gestionetratte.repository.tratta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.prova.gestionetratte.model.Tratta;

public class TrattaRepositoryCustomImpl implements TrattaRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Tratta> findByExample(Tratta example) {
		
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select a from Tratta a where a.id = a.id ");

		if (StringUtils.isNotEmpty(example.getCodice())) {
			whereClauses.add(" a.codice  like :codice ");
			paramaterMap.put("codice", "%" + example.getCodice() + "%");
		}
		if (StringUtils.isNotEmpty(example.getDescrizione())) {
			whereClauses.add(" a.descrizione like :descrizione ");
			paramaterMap.put("descrizione", "%" + example.getDescrizione() + "%");
		}
		if (example.getData() != null) {
			whereClauses.add("a.data >= :data ");
			paramaterMap.put("data", example.getData());
		}
		if (example.getOraDecollo() != null) {
			whereClauses.add("a.oraDecollo >= :oraDecollo ");
			paramaterMap.put("oraDecollo", example.getOraDecollo());
		}
		if (example.getOraAtterraggio() != null) {
			whereClauses.add("a.oraAtterraggio >= :oraAtterraggio ");
			paramaterMap.put("oraAtterraggio", example.getOraAtterraggio());
		}
		if (example.getStato()!=null) {
			whereClauses.add(" a.stato like :stato ");
			paramaterMap.put("stato", "%" + example.getStato() + "%");
		}

		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Tratta> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tratta.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
		
	}

}
