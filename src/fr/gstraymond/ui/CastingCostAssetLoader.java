package fr.gstraymond.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import fr.gstraymond.hearthstone.card.search.R;

public class CastingCostAssetLoader {

	private static final String PATH = "cc/hd";
	
	private Context context;
	private Map<String, Drawable> assets;
	private boolean init = false;

	public void init(Context context) {
		if (! init) {
			init = true;
			this.context = context;
			this.assets = new HashMap<String, Drawable>();
	
			try {
				for (String assetPath : context.getAssets().list(PATH)) {
					Drawable drawable = getDrawable(PATH + "/" + assetPath);
					drawable.setBounds(0, 0, getAssetSize(), getAssetSize());
					assets.put(assetPath, drawable);
				}
			} catch (IOException e) {
				Log.e(getClass().getName(), "init", e);
			}
		}
	}

	private Drawable getDrawable(String assetPath) throws IOException {
		return Drawable.createFromStream(getAssetManager().open(assetPath),
				null);
	}
	
	public Drawable get(String file) {
		return assets.get(file);
	}

	public Map<String, Drawable> getAssets() {
		return assets;
	}

	public void setAssets(Map<String, Drawable> assets) {
		this.assets = assets;
	}

	private AssetManager getAssetManager() {
		return context.getResources().getAssets();
	}

	private int getAssetSize() {
		return (int) context.getResources().getDimension(R.dimen.castingCostSize);
	}
}
