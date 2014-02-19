package fr.gstraymond.search.model.request;

import fr.gstraymond.biz.SearchOptions;

public class QueryString {

	private Query_string query_string;

	public QueryString(SearchOptions options) {
		this.query_string = new Query_string(options);
	}

	public Query_string getQuery_string() {
		return query_string;
	}

	public void setQuery_string(Query_string query_string) {
		this.query_string = query_string;
	}
}
