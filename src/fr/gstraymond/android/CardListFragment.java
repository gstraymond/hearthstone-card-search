package fr.gstraymond.android;

import static fr.gstraymond.constants.Consts.CARD_LIST;

import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import fr.gstraymond.search.model.response.Card;
import fr.gstraymond.ui.CardArrayAdapter;
import fr.gstraymond.ui.CastingCostAssetLoader;

public class CardListFragment extends ListFragment {

	private List<Card> cards; 

	private static final String STATE_ACTIVATED_POSITION = "activated_position";
	private Callbacks callbacks = dummyCallbacks;
	private int position = ListView.INVALID_POSITION;
	private ArrayAdapter<Card> arrayAdapter;

	public interface Callbacks {
		public void onItemSelected(Parcelable id);
	}

	private static Callbacks dummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(Parcelable id) {
		}
	};

	public CardListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		cards = getArguments().getParcelableArrayList(CARD_LIST);

		CustomApplication applicationContext = (CustomApplication) getActivity().getApplicationContext();
		CastingCostAssetLoader castingCostAssetLoader = applicationContext.getCastingCostAssetLoader();
		
		arrayAdapter = new CardArrayAdapter(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text2, cards, castingCostAssetLoader);
		
		setListAdapter(arrayAdapter);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			int position = savedInstanceState.getInt(STATE_ACTIVATED_POSITION);
			setActivatedPosition(position);
		}

		CardListActivity activity = (CardListActivity) getActivity();
		getListView().setOnScrollListener(activity.getEndScrollListener());
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
		callbacks.onItemSelected(cards.get(position));
//		getListView().setItemChecked(position, true);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (position != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, position);
		}
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(position, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		this.position = position;
	}
	
	public void appendCards(List<Card> cards) {
		arrayAdapter.addAll(cards);
		arrayAdapter.notifyDataSetChanged();
	}
	
	public int getCardListCount() {
		return arrayAdapter.getCount();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// select the first element
		if (isTablet() && getListAdapter().getCount() > 0) {
			long itemId = getListAdapter().getItemId(0);
			View view = getListAdapter().getView(0, null, null);
			getListView().performItemClick(view, 0, itemId);	
		}
	}
	
	private boolean isTablet() {
		CustomApplication application = (CustomApplication) getActivity().getApplication();
		return application.isTablet();
	}
}
