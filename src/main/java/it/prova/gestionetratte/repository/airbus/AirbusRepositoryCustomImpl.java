package it.prova.gestionetratte.repository.airbus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.prova.gestionetratte.model.Airbus;

public class AirbusRepositoryCustomImpl implements AirbusRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Airbus> findByExample(Airbus example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select a from Airbus a where a.id = a.id ");

		if (StringUtils.isNotEmpty(example.getCodice())) {
			whereClauses.add(" a.codice  like :codice ");
			paramaterMap.put("codice", "%" + example.getCodice() + "%");
		}
		if (StringUtils.isNotEmpty(example.getDescrizione())) {
			whereClauses.add(" a.descrizione like :descrizione ");
			paramaterMap.put("descrizione", "%" + example.getDescrizione() + "%");
		}
		if (example.getNumeroPasseggeri() != null && example.getNumeroPasseggeri() > 0)  {
			whereClauses.add(" a.numeroPasseggeri >= :numeroPasseggeri ");
			paramaterMap.put("numeroPasseggeri", example.getNumeroPasseggeri());
		}
		if (example.getDataInizioServizio() != null) {
			whereClauses.add("a.dataInizioServizio >= :dataInizioServizio ");
			paramaterMap.put("dataInizioServizio", example.getDataInizioServizio());
		}

		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Airbus> typedQuery = entityManager.createQuery(queryBuilder.toString(), Airbus.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}
}
