package fr.gstraymond.biz;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.text.Html.ImageGetter;
import android.view.View;
import android.widget.TextView;

public class URLImageParser implements ImageGetter {
    private TextView textView;

    public URLImageParser(TextView view) {
        this.textView = view;
    }

	@Override
    public Drawable getDrawable(String source) {
        LevelListDrawable drawable = new LevelListDrawable();
        Drawable empty = textView.getResources().getDrawable(android.R.drawable.ic_dialog_info);
        drawable.addLevel(0, 0, empty);
        drawable.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        new ImageGetterAsyncTask(textView, drawable, source).execute();

        return drawable;
    }

	public View getTextView() {
		return textView;
	}

	public void setTextView(TextView view) {
		this.textView = view;
	}

}

