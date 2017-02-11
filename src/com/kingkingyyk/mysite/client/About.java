package com.kingkingyyk.mysite.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class About extends VerticalPanel {
	
	public About(String titleStr) {
		setStyleName("aboutPanel");
		getElement().setAttribute("unselectable","on");
		getElement().setAttribute("onselectstart","return false;");
		getElement().setAttribute("ondrag","return false;");
		
		Label lblTitle=new Label(titleStr);
			lblTitle.setStyleName("aboutPanelTitle");
		add(lblTitle);
	}
	
	public void addParagraph(String s) {
		Label text=new Label(s);
			text.setStyleName("aboutPanelText");
		add(text);
	}
	
	public void addSubtext(String s) {
		Label text=new Label(s);
			text.setStyleName("aboutPanelSubtext");
		add(text);
	}
	
	public void addHTML(String s) {
		HTML text=new HTML(s);
			text.setStyleName("aboutPanelText");
		add(text);
	}
	
	public void addBlankLine() {
		addHTML("<br/>");
	}
	
	public void addList(String [] s) {
		String code="<ul>";
		for (String txt : s) code+=("<li>"+txt+"</li>");
		code+="</ul>";
		addHTML(code);
	}
	
	public void addImage(String url) {
		Image img=new Image();
			img.setStyleName("aboutPanelImage");
			img.setUrl(url);
		add(img);
	}
}
