package fr.gstraymond.search.model.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fr.gstraymond.biz.SearchOptions;

public class Bool {

	private List<Object> must;

	public Bool(SearchOptions options) {
		must = new ArrayList<Object>();
		must.add(new QueryString(options));

		Map<String, List<String>> facets = options.getFacets();
		for (Entry<String, List<String>> entry : facets.entrySet()) {
			for (String term : entry.getValue()) {
				Map<String, String> termMap = new HashMap<String, String>();
				termMap.put(entry.getKey(), term);
				must.add(new Term(termMap));
			}
		}
	}

	public List<Object> getMust() {
		return must;
	}

	public void setMust(List<Object> must) {
		this.must = must;
	}
}
