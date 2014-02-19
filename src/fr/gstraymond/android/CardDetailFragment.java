package fr.gstraymond.android;

import static fr.gstraymond.constants.Consts.CARD;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import fr.gstraymond.search.model.response.Card;
import fr.gstraymond.ui.SetArrayAdapter;

public class CardDetailFragment extends ListFragment {

	private Card card;
	private Callbacks callbacks = dummyCallbacks;
	
	public interface Callbacks {
		public void onItemSelected(int id);
	}

	private static Callbacks dummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(int id) {
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		card = getArguments().getParcelable(CARD);
		
		List<Object> objects = new ArrayList<Object>();
		objects.add(card);
		objects.addAll(card.getPublications());

		ListAdapter arrayAdapter = new SetArrayAdapter(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text2, objects);
		setListAdapter(arrayAdapter);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		callbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		callbacks = dummyCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		callbacks.onItemSelected(position);
	}
}
