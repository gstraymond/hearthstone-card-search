package fr.gstraymond.search.model.request;

import fr.gstraymond.biz.SearchOptions;

public class Query {

	private Query_string query_string;

	private Bool bool;

	public Query(String text) {
		this.query_string = new Query_string(text);
	}

	public Query(SearchOptions options) {
		bool = new Bool(options);
	}

	public Bool getBool() {
		return bool;
	}

	public void setBool(Bool bool) {
		this.bool = bool;
	}

	public Query_string getQuery_string() {
		return query_string;
	}

	public void setQuery_string(Query_string query_string) {
		this.query_string = query_string;
	}
}
