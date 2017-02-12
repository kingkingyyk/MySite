package com.kingkingyyk.mysite.client;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Grid;
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
	
	public Label addParagraph(String s) {
		Label text=new Label(s);
			text.setStyleName("aboutPanelText");
		add(text);
		return text;
	}
	
	public Label addSubtext(String s) {
		Label text=new Label(s);
			text.setStyleName("aboutPanelSubtext");
		add(text);
		return text;
	}
	
	public HTML addHTML(String s) {
		HTML text=new HTML(s);
			text.setStyleName("aboutPanelText");
		add(text);
		return text;
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
	
	private static String [] uvaUserInfoList={"rank","name","username","ac","nos"};
	public void initialize(MySite ms) {
		this.addParagraph("Hi, I'm a technology enthusiast based in Kuala Lumpur that believes in life-long hands-on learning. "
						+ "My journey of computing started at 5 years old. "
						+ "Years later, I started PC building and programming during my middle school, and became an Elite member in Lowyat.NET, the popular Malaysian forum. "
						+ "Being one of the national top scorer of Malaysian Higher Education Certificate, I managed to enter the top university in Malaysia, "
						+ "University of Malaya, and subsequently graduated from the studies of Computer Science (Computer System"
						+ " & Networking) at top ranking. Now, I'm working as test engineer in Quintiq.");
		this.addBlankLine();
		this.addImage("https://c1.staticflickr.com/9/8031/29681841892_35e3d7ffda_h.jpg");
		this.addSubtext("My hostelmates & me. I'm at somewhere behind! ;)");
		this.addBlankLine();
		this.addParagraph("I have experience in :");
		this.addList(new String [] {"PC Building, Consultation & Troubleshooting","vJass","Java (SE 8, Swing, SWT & FX)","Competitive Programming",
								  "Oracle SQL","HyperSQL","Hibernate Framework","Apache Cassandra","Apache Spark","GWT & CSS (this website is built on it!)",
								  "Cisco Routing & Switching (sub-CCNP & CCDA level)","OSI Layers Definition & Customization","VHDL",
								  "Internet of Things Automation (Arduino, ARM mbed, Raspberry Pi & related circuits)","HP Quality Center",
								  "VBScript"});
		this.addParagraph("During free time, I solve algorithm problems in various website (the main focus now is UVA), "
						+"build automation tool that removes the repetitive tasks in daily life, and study the underlying architecture and working mechanism of technologies. "
						+"Sometimes I watch thoughtful dramas and movies! Educational videos like SciShow will catch my attention too. "
						+"In case you wonder of the photos you viewed on first sight, I'm a casual shooter with occasional assignments.");
		this.addBlankLine();
		
		final VerticalPanel uvaRankPanel=new VerticalPanel();
			final HTML lblUVARank=new HTML("My Current UVA Ranking (Streamed from uHunt) :<br/>");
			uvaRankPanel.add(lblUVARank);
				lblUVARank.setStyleName("aboutPanelText");
			uvaRankPanel.add(lblUVARank);
			final Grid g=new Grid();
				g.setStyleName("aboutPanelGrid");
				g.resize(1, uvaUserInfoList.length);
				for (int i=0;i<uvaUserInfoList.length;i++) {
					Label lbl=new Label(uvaUserInfoList[i].substring(0,1).toUpperCase()+uvaUserInfoList[i].substring(1));
					lbl.setStyleName("aboutPanelGridTitle");
					g.setWidget(0,i,lbl);
				}
			uvaRankPanel.add(g);
			
			uvaRankPanel.add(new HTML("<br/>"));
			uvaRankPanel.add(new HTML("<br/>"));
			uvaRankPanel.setVisible(false);
		this.add(uvaRankPanel);
			
		this.addParagraph("Need recommendation on PC/laptop/camera/phone? Want to hop on competitive programming/IoT? Feel free to PM me on Lowyat.NET.");
		
		ms.dataQuery.getUVARankList("kingkingyyk3",new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {}
			
			@Override
			public void onSuccess(String result) {
				if (result==null) return;
				
				uvaRankPanel.setVisible(true);
				JSONArray data=JSONParser.parseStrict(result).isArray();
				g.resizeRows(data.size()+1);
				
				//put data into table
				for (int i=0;i<data.size();i++) {
					JSONObject obj=data.get(i).isObject();
					for (int i2=0;i2<uvaUserInfoList.length;i2++)  {
						Label lbl=new Label(obj.get(uvaUserInfoList[i2]).isString()!=null ? obj.get(uvaUserInfoList[i2]).isString().stringValue() : obj.get(uvaUserInfoList[i2]).toString());
						lbl.setStyleName("aboutPanelGridText");
						g.setWidget(i+1,i2,lbl);
					}
				}
				
				//search my username and put bold on it.
				int usernameColIndex=-1;
				for (int i=0;i<uvaUserInfoList.length && usernameColIndex==-1;i++) if (uvaUserInfoList[i].equals("username")) usernameColIndex=i;
				if (usernameColIndex==-1) Window.alert("About.java : Fail to get username from uvaUserInfoList");
				
				int usernameRowIndex=-1;
				for (int i=0;i<g.getRowCount();i++) if (g.getText(i,usernameColIndex).equals("kingkingyyk3")) usernameRowIndex=i;
				if (usernameRowIndex==-1) Window.alert("About.java : Fail to get my username");
				
				g.getRowFormatter().getElement(usernameRowIndex).getStyle().setFontWeight(FontWeight.BOLD);
				//==
			}
		});
	}
}
