package fr.gstraymond.biz;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;
import android.util.Log;
import fr.gstraymond.hearthstone.card.search.R;

public class SetImageGetter implements ImageGetter {

	private static final String PATH = "sets";
	private Context context;

	public SetImageGetter(Context context) {
		this.context = context;
	}

	@Override
	public Drawable getDrawable(String assetPath) {
		try {
			Drawable drawable = Drawable.createFromStream(getAssetManager().open(PATH + "/" + assetPath),
					null);
			drawable.setBounds(0, 0, getWidth(drawable), getAssetSize());
			return drawable;
		} catch (IOException e) {
			Log.e(getClass().getName(), "getDrawable", e);
		}
		return null;
	}

	private AssetManager getAssetManager() {
		return context.getResources().getAssets();
	}

	private int getAssetSize() {
		return (int) context.getResources().getDimension(R.dimen.castingCostSize);
	}
	
	private int getWidth(Drawable drawable) {
		int height = getAssetSize();
		int dHeight = drawable.getIntrinsicHeight();
		int dWidth = drawable.getIntrinsicWidth();
		return dWidth * height / dHeight;
	}

}
