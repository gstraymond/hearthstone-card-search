package fr.gstraymond.android;

import static fr.gstraymond.constants.Consts.CARD;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import fr.gstraymond.search.model.response.Card;

public class CardDetailFragment extends ListFragment {

	private Card card;
	
	public interface Callbacks {
		public void onItemSelected(int id);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		card = getArguments().getParcelable(CARD);
		
		List<Card> objects = new ArrayList<Card>();
		objects.add(card);

		ListAdapter arrayAdapter = new ArrayAdapter<Card>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text2, objects);
		setListAdapter(arrayAdapter);
	}
}
