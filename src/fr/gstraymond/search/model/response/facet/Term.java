package fr.gstraymond.search.model.response.facet;

public class Term {

	private String term;
	private int count;

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
