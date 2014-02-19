package fr.gstraymond.search.model.response;

import java.util.Map;

import fr.gstraymond.search.model.response.facet.Facet;

public class SearchResult {
	
	private int took;
	private Hits hits;
	private Map<String, Facet> facets;
	
	public int getTook() {
		return took;
	}
	
	public void setTook(int took) {
		this.took = took;
	}
	
	public Hits getHits() {
		return hits;
	}
	
	public void setHits(Hits hits) {
		this.hits = hits;
	}

	public Map<String, Facet> getFacets() {
		return facets;
	}

	public void setFacets(Map<String, Facet> facets) {
		this.facets = facets;
	}
}