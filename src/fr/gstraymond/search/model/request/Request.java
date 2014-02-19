package fr.gstraymond.search.model.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fr.gstraymond.biz.SearchOptions;
import fr.gstraymond.constants.FacetConst;
import fr.gstraymond.search.model.request.facet.Facet;

public class Request {

	private Query query;
	private Integer from;
	private Integer size;
	private Map<String, Facet> facets;
	private List<String> sort;

	public Request(SearchOptions options) {
		this.query = new Query(options);
		this.from = options.getFrom();
		this.size = options.getSize();
		// if appending results, facets don't need to be recalculated
		if (! options.isAppend()) {
			this.facets = FacetConst.getFacets();
			for (Entry<String, Integer> facetSize : options.getFacetSize().entrySet()) {
				Facet facet = this.facets.get(facetSize.getKey());
				facet.getTerms().setSize(facetSize.getValue());
			}
		}
		
		this.sort = new ArrayList<String>();
		if (SearchOptions.QUERY_ALL.equals(options.getQuery())) {
			// default sort
			this.sort.add("_uid");
		}
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Map<String, Facet> getFacets() {
		return facets;
	}

	public List<String> getSort() {
		return sort;
	}
}
