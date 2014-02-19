package fr.gstraymond.android;

import java.io.InputStream;

import android.os.Bundle;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;
import fr.gstraymond.hearthstone.card.search.R;
import fr.gstraymond.biz.CastingCostImageGetter;
import fr.gstraymond.search.help.HelpText;
import fr.gstraymond.tools.HelpFormatter;
import fr.gstraymond.tools.MapperUtil;
import fr.gstraymond.ui.CastingCostAssetLoader;

public class HelpActivity extends CustomActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		CastingCostImageGetter imageGetter = new CastingCostImageGetter(getCCAssetLoader());
		HelpFormatter formatter = new HelpFormatter(imageGetter);
		
		Spanned text = formatter.format(getHelpText());

		TextView view = getTextView();
		view.setText(text);
		// rends les liens cliquables + scroll
		view.setMovementMethod(LinkMovementMethod.getInstance());

		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private CastingCostAssetLoader getCCAssetLoader() {
		return getCustomApplication().getCastingCostAssetLoader();
	}

	private HelpText getHelpText() {
		InputStream stream = getResources().openRawResource(R.raw.help);
		return new MapperUtil<HelpText>(getObjectMapper(), HelpText.class).read(stream);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private TextView getTextView() {
		return (TextView) findViewById(R.id.help_text_view);
	}
}
