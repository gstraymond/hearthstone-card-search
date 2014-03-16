package fr.gstraymond.tools;

import android.content.Context;

public class DrawableManager {

	private Context context;

	public DrawableManager(Context context) {
		this.context = context;
	}

	public int getDrawableId(String drawableName) {
		String drawableId = context.getPackageName() + ":drawable/" + drawableName;
		return context.getResources().getIdentifier(drawableId, null, null);
	}
}
