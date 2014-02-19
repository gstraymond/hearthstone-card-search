package fr.gstraymond.ui;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import fr.gstraymond.hearthstone.card.search.R;
import fr.gstraymond.android.CardListActivity;
import fr.gstraymond.biz.SearchOptions;
import fr.gstraymond.biz.SearchProcessor;
import fr.gstraymond.search.model.response.facet.Term;

public class FacetOnChildClickListener implements OnChildClickListener {

	FacetListAdapter adapter;
	SearchOptions options;
	CardListActivity activity;

	public FacetOnChildClickListener(FacetListAdapter adapter,
			SearchOptions options, CardListActivity activity) {
		this.adapter = adapter;
		this.options = options;
		this.activity = activity;
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		Term term = adapter.getTerm(groupPosition, childPosition);
		String facet = adapter.getFacet(groupPosition);
		int textId = R.string.loading_facet;
		
		options.setAppend(false);
		options.setFrom(0);

		if (term.getCount() > -1) {
			if (adapter.isTermSelected(term)) {
				options.removeFacet(facet, term.getTerm());
			} else {
				options.addFacet(facet, term.getTerm());
			}
		} else {
			options.addFacetSize(facet);
			textId = R.string.loading_more_facet;
		}

		new SearchProcessor(activity, options, textId).execute();
		return true;
	}

}
