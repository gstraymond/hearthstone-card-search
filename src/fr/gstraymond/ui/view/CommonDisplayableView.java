package fr.gstraymond.ui.view;

import fr.gstraymond.api.ui.view.DisplayableView;
import android.view.View;

public abstract class CommonDisplayableView implements DisplayableView {
	
	protected View parentView;

	@Override
	public void setParentView(View parentView) {
		this.parentView = parentView;
	}

	@Override
	public View getView() {
		return parentView.findViewById(getId());
	}

	private boolean show() {
		getView().setVisibility(View.VISIBLE);
		return true;
	}

	private boolean hide() {
		getView().setVisibility(View.GONE);
		return false;
	}

	public boolean display(boolean display) {
		return display ? show() : hide();
	}
}
