package fr.gstraymond.search.model.response;

import java.util.List;

public class Hits {

	private int total;
	private List<Hit> hits;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<Hit> getHits() {
		return hits;
	}
	public void setHits(List<Hit> hits) {
		this.hits = hits;
	}
}
