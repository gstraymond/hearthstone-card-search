package fr.gstraymond.search.model.request.facet;

public class Facet {

	private Term terms;

	public Facet(String facet) {
		this.terms = new Term(facet);
	}

	public Term getTerms() {
		return terms;
	}

	public void setTerms(Term terms) {
		this.terms = terms;
	}

	@Override
	public String toString() {
		return "Facet[" + terms + "]";
	}

}
