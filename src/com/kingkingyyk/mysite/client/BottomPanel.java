package com.kingkingyyk.mysite.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

public class BottomPanel extends HorizontalPanel {

	public BottomPanel () {
		setStyleName("bottomBar");
	}
	
	public Image addClickableIcon (String iconUrl, String hoverText, final String hyperlink) {
		Image img=new Image(iconUrl);
			img.setStyleName("bottomBarIconClickable");
			img.setAltText(hoverText);
			img.setTitle(hoverText);
			img.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Window.open(hyperlink,"_blank","");
				}
				
			});
		add(img);
		return img;
	}
	
	public void addButton (Button btn) {
		btn.setStyleName("bottomBarButton");
		add(btn);
	}
	
	public void addClickableLabel (Label lbl) {
		lbl.setStyleName("bottomBarTextClickable");
		add(lbl);
	}
	
	public void addUnclickableLabel (Label lbl) {
		lbl.setStyleName("bottomBarTextUnclickable");
		add(lbl);
	}
	
	public Label addClickableText (String text) {
		Label lbl=new Label(text);
			lbl.setStyleName("bottomBarTextClickable");
		add(lbl);
		return lbl;
	}
	
	public Label addUnclickableText (String text) {
		Label lbl=new Label(text);
			lbl.setStyleName("bottomBarTextNotClickable");
		add(lbl);
		return lbl;
	}
	
	public Label addSeperator (String text) {
		Label seperator=new Label(text);
			seperator.setStyleName("bottomBarSeperator");
		add(seperator);
		return seperator;
	}
	
	public ListBox addListBox () {
		ListBox box=new ListBox();
			box.setStyleName("bottomBarListBox");
		add(box);
		return box;
	}
}
