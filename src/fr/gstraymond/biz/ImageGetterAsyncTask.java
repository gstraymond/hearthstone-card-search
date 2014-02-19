//package fr.gstraymond.biz;
//
//import java.io.InputStream;
//import java.net.URL;
//
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.AsyncTask;
//import android.util.Log;
//import android.widget.TextView;
//
//public class ImageGetterAsyncTask extends AsyncTask<Void, Void, BitmapDrawable> {
//	
//    private final static String TAG = ImageGetterAsyncTask.class.getName();
//
//	private Drawable drawable;
//    private TextView textView;
//    private String source;
//    
//    public ImageGetterAsyncTask(TextView textView, Drawable drawable, String source) {
//		this.textView = textView;
//		this.drawable = drawable;
//		this.source = source;
//	}
//
//    @Override
//    protected BitmapDrawable doInBackground(Void... params) {
//        Log.d(TAG, "doInBackground " + source);
//        try {
//            InputStream is = new URL(source).openStream();
//            return new BitmapDrawable(textView.getResources(), is);
//        } catch (Exception e) {
//        	Log.e(TAG, "error", e);
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(BitmapDrawable bitmapDrawable) {
//        if (bitmapDrawable != null) {
//        	
//            Bitmap bitmap = bitmapDrawable.getBitmap();
//            Canvas canvas = new Canvas(bitmap.copy(Bitmap.Config.ARGB_8888, true));
//			drawable.setBounds(0, 0, calculateWidth(), calculateHeight(bitmap));
//            drawable.setVisible(true, true);
//        	drawable.draw(canvas);
//
////            drawable.invalidateSelf();
//            
//            // redraw view
////            textView.invalidate();
//            textView.invalidateDrawable(drawable);
////            textView.refreshDrawableState();
////            textView.postInvalidate();
//
//          textView.setText(textView.getText());
//        }
//    }
//    
//    private int calculateWidth() {
//    	return changeSizeFactor(textView.getWidth());
//    }
//    
//    private int calculateHeight(Bitmap bitmap) {
//    	int height = textView.getWidth() * bitmap.getHeight() / bitmap.getWidth();
//		return changeSizeFactor(height);
//    }
//    
//    private int changeSizeFactor(int size) {
//    	return (int) (size * 0.9);
//    }
//}

package fr.gstraymond.biz;

import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class ImageGetterAsyncTask extends AsyncTask<Void, Void, BitmapDrawable> {
	
    private final static String TAG = ImageGetterAsyncTask.class.getName();

	private LevelListDrawable drawable;
    private TextView textView;
    private String source;
    
    public ImageGetterAsyncTask(TextView textView, LevelListDrawable drawable, String source) {
		this.textView = textView;
		this.drawable = drawable;
		this.source = source;
	}

    @Override
    protected BitmapDrawable doInBackground(Void... params) {
        Log.d(TAG, "doInBackground " + source);
        try {
            InputStream is = new URL(source).openStream();
            return new BitmapDrawable(textView.getResources(), is);
        } catch (Exception e) {
        	Log.e(TAG, "error", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(BitmapDrawable bitmapDrawable) {
        if (bitmapDrawable != null) {
            drawable.addLevel(1, 1, bitmapDrawable);
            drawable.setBounds(0, 0, calculateWidth(), calculateHeight(bitmapDrawable.getBitmap()));
            drawable.setLevel(1);
            
            // redraw view
            textView.setText(textView.getText());
        }
    }
    
    private int calculateWidth() {
    	return changeSizeFactor(textView.getWidth());
    }
    
    private int calculateHeight(Bitmap bitmap) {
    	int height = textView.getWidth() * bitmap.getHeight() / bitmap.getWidth();
		return changeSizeFactor(height);
    }
    
    private int changeSizeFactor(int size) {
    	return (int) (size * 0.9);
    }
}