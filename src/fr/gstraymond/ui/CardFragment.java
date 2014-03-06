package fr.gstraymond.ui;

import static fr.gstraymond.constants.Consts.CARD;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import fr.gstraymond.android.CustomApplication;
import fr.gstraymond.biz.PictureDownloader;
import fr.gstraymond.cache.BitmapCache;
import fr.gstraymond.hearthstone.card.search.R;
import fr.gstraymond.search.model.response.Card;

public class CardFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_card, container, false);
		ImageView imageView = (ImageView) rootView.findViewById(R.id.fragment_card_picture);

		Card card = getArguments().getParcelable(CARD);
		new PictureDownloader(imageView, card.getImage(), getCache()).execute();

		return rootView;
	}

	private BitmapCache getCache() {
		CustomApplication application = (CustomApplication) getActivity()
				.getApplication();
		return application.getBitmapCache();
	}
}
