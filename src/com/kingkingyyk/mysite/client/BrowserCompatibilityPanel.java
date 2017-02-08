package com.kingkingyyk.mysite.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;

public class BrowserCompatibilityPanel extends PopupPanel {
	private HTML txt;
	  
	public BrowserCompatibilityPanel() {
		setStyleName("popupPanel");
		txt=new HTML();
		txt.setStyleName("bottomBarTextNotClickable");
		add(txt);
		setAnimationEnabled(true);
		setAutoHideEnabled(true);
		setGlassEnabled(true);
		hide();
		
		getGlassElement().getStyle().setProperty("width","100%");
		getGlassElement().getStyle().setProperty("height","100%");
		getGlassElement().getStyle().setBackgroundColor("black");
		getGlassElement().getStyle().setOpacity(0.5);
	}
	
	public void setText(String s) {
		txt.setHTML(s);
	}
}
