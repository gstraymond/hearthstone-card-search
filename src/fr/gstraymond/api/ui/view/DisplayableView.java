package fr.gstraymond.api.ui.view;

import android.view.View;
import fr.gstraymond.search.model.response.Card;

public interface DisplayableView {

	public boolean display(Card card);
	public View getView();
	public int getId();

	public void setValue(Card card);
	public void setParentView(View parentView);
}
