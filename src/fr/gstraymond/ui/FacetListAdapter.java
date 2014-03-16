package fr.gstraymond.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import fr.gstraymond.biz.SearchOptions;
import fr.gstraymond.constants.FacetConst;
import fr.gstraymond.hearthstone.card.search.R;
import fr.gstraymond.search.model.response.facet.Facet;
import fr.gstraymond.search.model.response.facet.Term;


public class FacetListAdapter extends BaseExpandableListAdapter {

	private List<String> facetList;
	private List<String> selectedFacets;
	private Map<String, Facet> facetMap;
	private List<Term> selectedTerms;
	private SearchOptions options;

	public FacetListAdapter(Map<String, Facet> facets, SearchOptions options, Context context) {
		this.facetMap = facets;
		this.selectedFacets = new ArrayList<String>();
		this.facetList = new ArrayList<String>();
		this.selectedTerms = new ArrayList<Term>();
		this.options = options;

		if (facets == null) {
			return;
		}
		
		for (String facetAsString : FacetConst.getFacetOrder()) {
			Facet facet = facets.get(facetAsString);
			
			if (facet == null || facet.getTerms().isEmpty()) {
				facetMap.remove(facetAsString);
				continue;
			}

			facetList.add(facetAsString); 
			List<Term> facetTerms = facet.getTerms();
			
			if (! facetTerms.isEmpty()) {
				List<String> termsAsString = options.getFacets().get(facetAsString);
				if (termsAsString != null) {
					selectedFacets.add(facetAsString);
					selectedTerms.addAll(findTerms(termsAsString, facetTerms));
				}
			}
		}
		
		for (Entry<String, Facet> facetEntry : facetMap.entrySet()) {
			String facetAsString = facetEntry.getKey();
			Facet facet = facetEntry.getValue();
			if (showLoadMore(facet, facetAsString)) {
				Term loadMoreTerm = new Term();
				loadMoreTerm.setTerm(context.getString(R.string.facet_more));
				loadMoreTerm.setCount(-1);
				facet.getTerms().add(loadMoreTerm);
			}
		}
	}

	private boolean showLoadMore(Facet facet, String facetAsString) {
		Integer facetSizeRequested = options.getFacetSize().get(facetAsString);
		if (facetSizeRequested == null) {
			facetSizeRequested = 10;
		}

		Integer facetSize = facet.getTerms().size();
		return facetSize.equals(facetSizeRequested);
	}
	
	private List<Term> findTerms(List<String> termsAsString, List<Term> terms) {
		List<Term> termsFound = new ArrayList<Term>();
		
		for (String termAsString : termsAsString) {
			Term termFound = findTerm(termAsString, terms);
			if (termFound != null) {
				termsFound.add(termFound);
			}
		}
		
		return termsFound;
	}
	
	private Term findTerm(String termAsString, List<Term> terms) {
		for(Term term : terms) {
			if (term.getTerm().equals(termAsString)) {
				return term;
			}
		}
		return null;
	}

	private List<Term> getChildren(int groupPosition) {
		return facetMap.get(getGroup(groupPosition)).getTerms();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return getTerm(groupPosition, childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.drawer_child, null);
        }
        
		Term term = getTerm(groupPosition, childPosition);
		String text = term.getTerm();

		TextView textTextView = (TextView) view.findViewById(R.id.drawer_child_text);
		TextView counterTextViewInactive = (TextView) view.findViewById(R.id.drawer_child_counter_inactive);
		counterTextViewInactive.setVisibility(View.GONE);
		TextView counterTextViewActive = (TextView) view.findViewById(R.id.drawer_child_counter_active);
		counterTextViewActive.setVisibility(View.GONE);
		
		TextView counterTextView = counterTextViewInactive;
		
		if (selectedTerms.contains(term)) {
			counterTextView = counterTextViewActive;
			textTextView.setText(Html.fromHtml("<b>" + text + "</b>", null, null));
		} else {
			textTextView.setText(text);
		}
		
		counterTextView.setVisibility(View.VISIBLE);
		
		if (term.getCount() > 0) {
			counterTextView.setText(term.getCount() + "");
		} else {
			counterTextView.setText("?");
		}
		
		return view;
	}

	public Term getTerm(int groupPosition, int childPosition) {
		return getChildren(groupPosition).get(childPosition);
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return getChildren(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return getFacet(groupPosition);
	}

	public String getFacet(int groupPosition) {
		return facetList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return facetList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.drawer_group, null);
        }
		
        TextView textView = (TextView) view.findViewById(R.id.drawer_group_textview);
		String facet = facetList.get(groupPosition);
		
		if (selectedFacets.contains(facet) || options.getFacetSize().containsKey(facet)) {
			ExpandableListView expandableListView = (ExpandableListView) parent;
		    expandableListView.expandGroup(groupPosition);
		}
		
		textView.setText(FacetConst.getFacetName(facet, parent.getContext()));
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	public boolean isTermSelected(Term term) {
		return selectedTerms.contains(term);
	}
}
