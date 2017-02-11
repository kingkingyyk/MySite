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
	public static About ap;

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
		
		bp.addUnclickableText("kingkingyyk");
		bp.addSeperator("|").getElement().getStyle().setMarginRight(5,Unit.PX);
		bp.addButton(new AboutButton());
		bp.addSeperator("|").getElement().getStyle().setMarginLeft(5,Unit.PX);;
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
	
	public void setupAboutPanel() {
		ap=new About("About");
		
		ap.addParagraph("Hi, I'm a technology enthusiast based in Kuala Lumpur that believes in life-long hands-on learning. "
						+ "My journey of computing started at 5 years old. "
						+ "Years later, I started PC building and programming during my middle school, and became an Elite member in Lowyat.NET, the Malaysia top forum. "
						+ "This has accelerated my path to study about computing. Being one of the national top scorer of Malaysian Higher Education Certificate, "
						+ "I managed to enter the top university in Malaysia, University of Malaya, and subsequently graduated from the studies of Computer Science (Computer System"
						+ " & Networking) at top ranking. Now, I'm working as test engineer in Quintiq.");
		ap.addBlankLine();
		ap.addImage("https://c1.staticflickr.com/9/8031/29681841892_35e3d7ffda_h.jpg");
		ap.addSubtext("My hostelmates & me. I'm at somewhere behind! ;)");
		ap.addBlankLine();
		ap.addParagraph("I have experience in :");
		ap.addList(new String [] {"PC Building, Consultation & Troubleshooting","vJass","Java (SE 8, Swing, SWT & FX)","Competitive Programming",
								  "Oracle SQL","HyperSQL","Hibernate Framwork","Apache Cassandra","Apache Spark","GWT & CSS (this website is built on it!)",
								  "Cisco Routing & Switching (sub-CCNP & CCDA level)","OSI Layers Definition & Customization","VHDL",
								  "Internet of Things Automation (Arduino, ARM mbed, Raspberry Pi & related circuits)","HP Quality Center",
								  "VBScript"});
		ap.addParagraph("During free time, I solve algorithm problems in various website (the main focus now is UVA), "
						+"build automation tool that removes the repetitive tasks in daily life, and study the underlying architecture and working mechanism of technologies. "
						+"Sometimes I watch thoughtful dramas and movies! Educational videos like SciShow will catch my attention too. "
						+"In case you wonder of the photos you viewed on first sight, I'm a casual shooter with occasional assignments.");
		ap.addBlankLine();
		ap.addParagraph("Need recommendation on PC/laptop/camera/phone? Want to hop on competitive programming/IoT? Feel free to PM me on Lowyat.NET.");
	}
	
	public void onModuleLoad() {
		setupRootPanel();
		setupPictureShowcase();
		setupBottomPanel();
		setupAboutPanel();
	}
}
