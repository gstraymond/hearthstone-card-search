package fr.gstraymond.ui;

import static fr.gstraymond.constants.Consts.CARD;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import fr.gstraymond.android.CustomApplication;
import fr.gstraymond.biz.PictureDownloader;
import fr.gstraymond.cache.BitmapCache;
import fr.gstraymond.hearthstone.card.search.R;
import fr.gstraymond.search.model.response.Card;

public class CardFragment extends Fragment {
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

    	// FIXME use xml instead
        ImageView imageView = new ImageView(getActivity());
        imageView.setImageBitmap(getDefaultBitmap());
        imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ScaleType.CENTER);
        
		Card card = getArguments().getParcelable(CARD);
        
        new PictureDownloader(imageView, card.getImage(), getCache()).execute();
        
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        layout.setGravity(Gravity.CENTER);
        layout.addView(imageView); 
        
        return layout;
    }

	private Bitmap getDefaultBitmap() {
		return BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.picture);
	}
	
	private BitmapCache getCache() {
		CustomApplication application = (CustomApplication) getActivity().getApplication();
		return application.getBitmapCache();
	}
}
