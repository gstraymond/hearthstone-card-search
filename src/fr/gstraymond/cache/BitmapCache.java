package fr.gstraymond.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

public class BitmapCache {
	private LruCache<String, Bitmap> memLruCache;

	public BitmapCache() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory / 3;
		memLruCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount() / 1024;
			}
		};
	}

	public Bitmap get(String key) {
		return memLruCache.get(key);
	}

	public void put(String url, Bitmap bitmap) {
		memLruCache.put(url, bitmap);
	}
}
