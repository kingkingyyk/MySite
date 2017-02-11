package com.kingkingyyk.mysite.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

public class AboutButton extends Button {
	private static double transitionTime=0.2;
	
	public AboutButton () {
		setText("About");
		
		addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setText(getText().equals("Portfolio") ? "About" : "Portfolio");
				if (getText().equals("Portfolio")) {
					Timer t=new Timer() {
						private double opacity=1;
						private int maxCount=(int)(60*transitionTime);
						private int count=maxCount;
						private boolean fadeOutDone=false;
						@Override
						public void run() {
							if (!fadeOutDone) {
								opacity=opacity-(1.0/maxCount);
								MySite.ps.getElement().getStyle().setOpacity(opacity);
								MySite.albumSelect.getElement().getStyle().setOpacity(opacity);
								if (--count==0) {
									RootPanel.get().remove(MySite.ps);
									MySite.bp.remove(MySite.albumSelect);
									fadeOutDone=true;
									MySite.ap.getElement().getStyle().setOpacity(0.0);
									RootPanel.get().add(MySite.ap);
									count=maxCount;
								}
							} else {
								opacity=opacity+(1.0/maxCount);
								MySite.ap.getElement().getStyle().setOpacity(opacity);
								if (--count==0) cancel();
							}
						}
						
					};
					t.scheduleRepeating(17);
				} else {
					Timer t=new Timer() {
						private double opacity=1;
						private int maxCount=(int)(60*transitionTime);
						private int count=maxCount;
						private boolean fadeOutDone=false;
						@Override
						public void run() {
							if (!fadeOutDone) {
								opacity=opacity-(1.0/maxCount);
								MySite.ap.getElement().getStyle().setOpacity(opacity);
								if (--count==0) {
									RootPanel.get().remove(MySite.ap);
									fadeOutDone=true;
									MySite.ps.getElement().getStyle().setOpacity(0.0);
									MySite.albumSelect.getElement().getStyle().setOpacity(0.0);
									RootPanel.get().add(MySite.ps);
									MySite.bp.add(MySite.albumSelect);
									count=maxCount;
								}
							} else {
								opacity=opacity+(1.0/maxCount);
								MySite.ps.getElement().getStyle().setOpacity(opacity);
								MySite.albumSelect.getElement().getStyle().setOpacity(opacity);
								if (--count==0) cancel();
							}
						}
						
					};
					t.scheduleRepeating(17);
				}
			}
		});
	}
}
