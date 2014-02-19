package fr.gstraymond.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import fr.gstraymond.hearthstone.card.search.R;
import fr.gstraymond.search.model.request.facet.Facet;

public class FacetConst {

	private static final String ABILITY = "abilities.exact";
	private static final String ARTIST = "artists.exact";
	private static final String CMC = "convertedManaCost";
	private static final String COLOR = "colors.exact";
	private static final String DEVOTION = "devotions";
	private static final String SET = "editions.exact";
	private static final String FORMAT = "formats";
	private static final String POWER = "power";
	private static final String RARITY = "rarities";
	private static final String TOUGHNESS = "toughness";
	private static final String TYPE = "type";

	private static Map<String, Integer> facetNames;
	private static List<String> facetOrder;

	static {
		facetNames = new HashMap<String, Integer>();
		facetNames.put(ABILITY, R.string.facet_ability);
		facetNames.put(ARTIST, R.string.facet_artist);
		facetNames.put(COLOR, R.string.facet_color);
		facetNames.put(CMC, R.string.facet_cmc);
		facetNames.put(DEVOTION, R.string.facet_devotion);
		facetNames.put(FORMAT, R.string.facet_format);
		facetNames.put(POWER, R.string.facet_power);
		facetNames.put(RARITY, R.string.facet_rarity);
		facetNames.put(SET, R.string.facet_set);
		facetNames.put(TOUGHNESS, R.string.facet_toughness);
		facetNames.put(TYPE, R.string.facet_type);
		
		facetOrder = new ArrayList<String>();
		facetOrder.add(COLOR);
		facetOrder.add(DEVOTION);
		facetOrder.add(CMC);
		facetOrder.add(TYPE);
		facetOrder.add(ABILITY);
		facetOrder.add(POWER);
		facetOrder.add(TOUGHNESS);
		facetOrder.add(RARITY);
		facetOrder.add(SET);
		facetOrder.add(FORMAT);
		facetOrder.add(ARTIST);
	}

	private static void putInFacets(Map<String, Facet> facets, String facet) {
		facets.put(facet, new Facet(facet));
	}

	public static Map<String, Facet> getFacets() {
		Map<String, Facet> facets = new HashMap<String, Facet>();
		putInFacets(facets, ABILITY);
		putInFacets(facets, ARTIST);
		putInFacets(facets, COLOR);
		putInFacets(facets, CMC);
		putInFacets(facets, DEVOTION);
		putInFacets(facets, SET);
		putInFacets(facets, FORMAT);
		putInFacets(facets, POWER);
		putInFacets(facets, RARITY);
		putInFacets(facets, TOUGHNESS);
		putInFacets(facets, TYPE);
		return facets;
	}

	public static String getFacetName(String facet, Context context) {
		return context.getString(facetNames.get(facet));
	}
	
	public static List<String> getFacetOrder() {
		return facetOrder;
	}
}
