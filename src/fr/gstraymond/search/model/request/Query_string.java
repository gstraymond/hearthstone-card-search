package fr.gstraymond.search.model.request;

import fr.gstraymond.biz.SearchOptions;

public class Query_string {

	private String query;
	private String default_operator = "AND";

	public Query_string(String text) {
		query = text;
	}

	public Query_string(SearchOptions options) {
		query = options.getQuery();
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getDefault_operator() {
		return default_operator;
	}

	public void setDefault_operator(String default_operator) {
		this.default_operator = default_operator;
	}
}