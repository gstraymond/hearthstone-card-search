package fr.gstraymond.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import fr.gstraymond.hearthstone.card.search.R;
import fr.gstraymond.search.model.request.facet.Facet;

public class FacetConst {


	private static final String ATTACK = "attack";
	private static final String CAPABILITIES = "capabilities.exact";
	private static final String CASTING_COST = "castingCost";
	private static final String CLAZZ = "clazz.exact";
	private static final String HEALTH = "health";
	private static final String MINION_TYPE = "minionType.exact";
	private static final String RARITY = "rarity.exact";
	private static final String SET = "set.exact";
	private static final String TYPE = "type.exact";

	private static Map<String, Integer> facetNames;
	private static List<String> facetOrder;

	static {
		facetNames = new HashMap<String, Integer>();
		facetNames.put(ATTACK, R.string.facet_attack);
		facetNames.put(CAPABILITIES, R.string.facet_capabilities);
		facetNames.put(CASTING_COST, R.string.facet_casting_cost);
		facetNames.put(CLAZZ, R.string.facet_clazz);
		facetNames.put(HEALTH, R.string.facet_health);
		facetNames.put(MINION_TYPE, R.string.facet_minion_type);
		facetNames.put(RARITY, R.string.facet_rarity);
		facetNames.put(SET, R.string.facet_set);
		facetNames.put(TYPE, R.string.facet_type);
		
		facetOrder = new ArrayList<String>();
		facetOrder.add(CASTING_COST);
		facetOrder.add(CLAZZ);
		facetOrder.add(ATTACK);
		facetOrder.add(HEALTH);
		facetOrder.add(MINION_TYPE);
		facetOrder.add(SET);
		facetOrder.add(RARITY);
		facetOrder.add(TYPE);
		facetOrder.add(CAPABILITIES);
	}

	private static void putInFacets(Map<String, Facet> facets, String facet) {
		facets.put(facet, new Facet(facet));
	}

	public static Map<String, Facet> getFacets() {
		Map<String, Facet> facets = new HashMap<String, Facet>();
		putInFacets(facets, CASTING_COST);
		putInFacets(facets, CLAZZ);
		putInFacets(facets, ATTACK);
		putInFacets(facets, HEALTH);
		putInFacets(facets, MINION_TYPE);
		putInFacets(facets, SET);
		putInFacets(facets, RARITY);
		putInFacets(facets, TYPE);
		putInFacets(facets, CAPABILITIES);
		return facets;
	}

	public static String getFacetName(String facet, Context context) {
		return context.getString(facetNames.get(facet));
	}
	
	public static List<String> getFacetOrder() {
		return facetOrder;
	}
}
