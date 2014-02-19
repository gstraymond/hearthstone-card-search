package fr.gstraymond.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.util.Log;

public class VersionUtils {
	
	public static String getOsVersion() {
		return Build.VERSION.RELEASE;
	}
	
	public static String getAppVersion(Context context) {
		try {
			String packageName = context.getPackageName();
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			Log.e("VersionUtils", "getVersionName", e);
		}
		
		return null;
	}
}
