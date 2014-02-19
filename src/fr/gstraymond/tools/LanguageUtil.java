package fr.gstraymond.tools;

import android.content.Context;

public class LanguageUtil {

	public static boolean showFrench(Context context) {
		String language = context.getResources().getConfiguration().locale.getLanguage();
		return "fr".equals(language);
	}
}
