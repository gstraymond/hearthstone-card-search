package fr.gstraymond.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class SearchOptions implements Parcelable {

	public static final String QUERY_ALL = "*";
	
	private String query = QUERY_ALL;
	private boolean append = false;
	private int from = 0;
	private int size = 30;
	private Map<String, List<String>> facets = new HashMap<String, List<String>>();
	private Map<String, Integer> facetSize = new HashMap<String, Integer>();


	public static final Parcelable.Creator<SearchOptions> CREATOR = new Parcelable.Creator<SearchOptions>() {
		@Override
		public SearchOptions createFromParcel(Parcel source) {
			return new SearchOptions(source);
		}

		@Override
		public SearchOptions[] newArray(int size) {
			return new SearchOptions[size];
		}
	};

	public SearchOptions(Parcel source) {
		query = source.readString();
		append = source.readInt() == 0 ? true : false;
		from = source.readInt();
		size = source.readInt();
		facets = readMap(source);
		// facetSize : pas de persistence de la taille des facettes
	}

	private Map<String, List<String>> readMap(Parcel source) {
		Map<String, List<String>> facets = new HashMap<String, List<String>>();
		String facetsAsString = source.readString();
		
		if (facetsAsString == null || facetsAsString.isEmpty()) {
			return facets;
		}
		
		String[] firstSplit = facetsAsString.split("\\|");
		for (String facet : firstSplit) {
			String[] keyValues = facet.split("=");
			String key = keyValues[0];
			
			String valuesAsString = keyValues[1];
			String[] secondSplit = valuesAsString.split(",");
			List<String> values = new ArrayList<String>();
			for (String value : secondSplit) {
				values.add(value);
			}

			facets.put(key, values);
		}
		return facets;
	}

	public SearchOptions() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(query);
		dest.writeInt(append ? 0 : 1);
		dest.writeInt(from);
		dest.writeInt(size);
		writeMap(dest, facets);
		// facetSize : pas de persistence de la taille des facettes
	}

	private void writeMap(Parcel dest, Map<String, List<String>> facets) {
		StringBuilder facetsAsString = new StringBuilder();
		String firstSep = "";
		for (Entry<String, List<String>> entry : facets.entrySet()) {
			facetsAsString.append(firstSep);
			facetsAsString.append(entry.getKey());
			facetsAsString.append("=");
			String secondSep = "";
			for (String value : entry.getValue()) {
				facetsAsString.append(secondSep);
				facetsAsString.append(value);
				secondSep = ",";
			}
			firstSep = "|";
		}
		
		dest.writeString(facetsAsString.toString());
	}

	public String getQuery() {
		return query;
	}

	public SearchOptions setQuery(String query) {
		if (TextUtils.isEmpty(query)) {
			this.query = QUERY_ALL;
		} else {
			this.query = query;	
		}
		return this;
	}

	public boolean isAppend() {
		return append;
	}

	public SearchOptions setAppend(boolean append) {
		this.append = append;
		return this;
	}

	public Integer getFrom() {
		return from;
	}

	public SearchOptions setFrom(Integer from) {
		this.from = from;
		return this;
	}

	public Integer getSize() {
		return size;
	}

	public SearchOptions setSize(Integer size) {
		this.size = size;
		return this;
	}
	
	public SearchOptions addFacet(String facet, String term) {
		if (facets.containsKey(facet)) {
			facets.get(facet).add(term);	
		} else {
			List<String> terms = new ArrayList<String>();
			terms.add(term);
			facets.put(facet, terms);
		}
		return this;
	}

	public SearchOptions removeFacet(String facet, String term) {
		if (facets.containsKey(facet)) {
			facets.get(facet).remove(term);
			
			if (facets.get(facet).size() == 0) {
				facets.remove(facet);
			}
		}
		return this;
	}

	public SearchOptions addFacetSize(String facet) {
		if (facetSize.containsKey(facet)) {
			Integer size = facetSize.get(facet);
			facetSize.put(facet, size + 10);
		} else {
			facetSize.put(facet, 20);
		}
		return this;
	}
	
	public Map<String, List<String>> getFacets() {
		return facets;
	}
	
	public SearchOptions setFacets(Map<String, List<String>> facets) {
		this.facets = facets;
		return this;
	}

	public Map<String, Integer> getFacetSize() {
		return facetSize;
	}

	public void setFacetSize(Map<String, Integer> facetSize) {
		this.facetSize = facetSize;
	}

	@Override
	public String toString() {
		return "searchOptions:[" +
				"query:" + query + "," +
				"append:" + append + "," +
				"from:" + from + "," +
				"size:" + size + "," +
				"facets:" + facets + "]" + "," +
				"facetSize:" + facetSize + "]";
	}
}
