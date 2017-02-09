package com.kingkingyyk.mysite.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

public class MySite implements EntryPoint {

	public final GreetingServiceAsync dataQuery = GWT.create(GreetingService.class);
	public static PictureShowcase ps;
	public static BottomPanel bp;
	public static ListBox albumSelect;

	public void setupRootPanel() {
		RootPanel.get().getElement().getStyle().setMargin(0,Unit.PX);
		RootPanel.get().getElement().getStyle().setPadding(0,Unit.PX);
	}
	
	public void setupPictureShowcase() {
		ps=new PictureShowcase(this);
		RootPanel.get().add(ps);
	}
	
	public void setupBottomPanel() {
		bp=new BottomPanel();
		
		bp.addUnclickableText("kingkingyyk's Hideout");
		bp.addSeperator("|");
		bp.addClickableIcon("http://i.imgur.com/QPuoTbp.png", "FB", "https://www.facebook.com/kingkingyyk");
		bp.addClickableIcon("http://i.imgur.com/TxEvO7H.png", "Lowyat.NET", "https://forum.lowyat.net/index.php?showuser=160560");
		bp.addClickableIcon("http://i.imgur.com/TnFHiIi.png", "Flickr", "https://www.flickr.com/photos/106834848@N08/");
		bp.addClickableIcon("http://i.imgur.com/fuZsuDq.png", "Linkedin", "https://www.linkedin.com/in/yap-yee-king-947b24106");
		bp.addClickableIcon("http://i.imgur.com/scl3pMw.png", "GitHub", "https://github.com/kingkingyyk");
		bp.addClickableIcon("https://thumb.ibb.co/bzjg1F/uva.png", "uHunt (UVA)", "http://uhunt.felix-halim.net/id/422649");
		bp.addClickableIcon("https://image.ibb.co/bOznaa/uva.png", "Project Euler", "https://projecteuler.net/country=Malaysia");
		bp.addClickableIcon("http://i.imgur.com/WhqT9hx.png", "PC Recommendation & Builder", "http://1-dot-rigbuilder-141104.appspot.com/");
		bp.addSeperator("|");
		albumSelect=bp.addListBox();
		albumSelect.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				ps.changeAlbum(albumSelect.getSelectedItemText());
			}
		});
		
		RootPanel.get().add(bp);
	}
	
	public void onModuleLoad() {
		setupRootPanel();
		setupPictureShowcase();
		setupBottomPanel();
	}
}
