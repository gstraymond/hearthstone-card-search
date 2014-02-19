package fr.gstraymond.search.model.request.facet;

public class Term {

	private String field;
	private Integer size;

	public Term(String field) {
		this.field = field;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Override
	public String toString() {
		if (size != null) {
			return "Term=" + field + ";" + size;
		}
		return "Term=" + field;
	}
}
