package fr.gstraymond.biz;

import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;
import fr.gstraymond.ui.CastingCostAssetLoader;

public class CastingCostImageGetter implements ImageGetter {
	
	private CastingCostAssetLoader castingCostAssetLoader;

	public CastingCostImageGetter(CastingCostAssetLoader castingCostAssetLoader) {
		super();
		this.castingCostAssetLoader = castingCostAssetLoader;
	}

	@Override
	public Drawable getDrawable(String source) {
		return castingCostAssetLoader.get(source);
	}
}