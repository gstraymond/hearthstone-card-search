package fr.gstraymond.ui;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import fr.gstraymond.hearthstone.card.search.R;
import fr.gstraymond.android.CustomApplication;
import fr.gstraymond.biz.PictureDownloader;
import fr.gstraymond.cache.BitmapCache;

public class CardFragment extends Fragment {

	private static final String URL = "url";
	private String url;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

    	// FIXME use xml instead
        ImageView imageView = new ImageView(getActivity());
        imageView.setImageBitmap(getDefaultBitmap());
        imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ScaleType.CENTER);
        
        if (url == null) {
        	url = savedInstanceState.getString(URL);
    		Log.d(getClass().getName(), "onCreateView url restored " + url);
        }
        
        new PictureDownloader(imageView, url, getCache()).execute();
        
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

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(URL, url);
		Log.d(getClass().getName(), "onSaveInstanceState " + outState);
	}

	public Fragment setCardUrl(String url) {
		this.url = url;
		return this;
	}
}
