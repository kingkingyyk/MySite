package com.kingkingyyk.mysite.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PictureShowcase extends VerticalPanel {
	public static HashMap<String,ArrayList<String []>> imageList=new HashMap<>();
	private static int ImageTimeout=7000;
	private static String currentTag="All";
	private int currentIndex=-1;
	private HorizontalPanel picture;
	private HorizontalPanel backPanel;
	private HorizontalPanel nextPanel;
	private Label btnBack;
	private Label btnNext;
	private Label lblEquipment;
	private Label lblSettings;
	private Timer timerChangePicture;
	
	private static String addUrlTag (String s) {
		return "url('"+s+"')";
	}
	
	public PictureShowcase (MySite instance) {
		this.setStyleName("pictureShowcasePanel");
		updatePanelSize();
		
		final Label lblLoading=new Label("Loading...");
			lblLoading.setStyleName("pictureShowcaseLoadingText");
		this.add(lblLoading);
		
		instance.dataQuery.getPictureData(new AsyncCallback<ArrayList<String []>> () {
			@Override
			public void onFailure(Throwable caught) {
				lblLoading.setText("Loading failed.");
			}

			public void shuffleResult(ArrayList<String[]> result) {
				ArrayList<String []> temp=new ArrayList<>();
				temp.addAll(result);
				
				result.clear();
				while (temp.size()>0) result.add(temp.remove(Random.nextInt(temp.size())));
			}
			
			@Override
			public void onSuccess(ArrayList<String[]> result) {
				if (result.size()==0) {
					onFailure(null);
					return;
				}
				remove(lblLoading);
				shuffleResult(result);
				imageList.put(currentTag,result);
				
				HorizontalPanel hp=new HorizontalPanel();
					hp.setStyleName("pictureShowcaseViewport");
					
					backPanel=new HorizontalPanel();
						btnBack=new Label("<");
							btnBack.setStyleName("pictureShowcaseControl");
							btnBack.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									timerChangePicture.cancel();
									previousPicture();
									timerChangePicture.scheduleRepeating(ImageTimeout);
								}
							});
						backPanel.add(btnBack);
					hp.add(backPanel);
						
					picture=new HorizontalPanel();
						picture.setStyleName("pictureShowcasePicture");
					hp.add(picture);
				
					nextPanel=new HorizontalPanel();
						btnNext=new Label(">");
							btnNext.setStyleName("pictureShowcaseControl");
							btnNext.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									timerChangePicture.cancel();
									nextPicture();
									timerChangePicture.scheduleRepeating(ImageTimeout);
								}
							});
						nextPanel.add(btnNext);
					hp.add(nextPanel);
				
					updatePictureSize();
				add(hp);
				
				VerticalPanel vp=new VerticalPanel();
					vp.setStyleName("pictureShowcaseInformation");
						lblEquipment=new Label();
							lblEquipment.setStyleName("pictureShowcaseInformationText");
					vp.add(lblEquipment);
						
						lblSettings=new Label();
							lblSettings.setStyleName("pictureShowcaseInformationText");
					vp.add(lblSettings);
				add(vp);
				
				nextPicture();
				timerChangePicture=new Timer() {
					@Override
					public void run() {
						nextPicture();
					}
				};
				
				timerChangePicture.scheduleRepeating(ImageTimeout);
				
				for (String [] data : result) {
					for (String tag : data[3].split(",")) {
						tag=tag.trim();
						ArrayList<String []> currList=null;
						if (!imageList.containsKey(tag)) {
							currList=new ArrayList<>();
							imageList.put(tag,currList);
						} else {
							currList=imageList.get(tag);
						}
						currList.add(data);
					}
				}
				
				for (String name : getAlbumList()) MySite.albumSelect.addItem(name);
			}
			
		});
		
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				updatePanelSize();
				updatePictureSize();
			}
		});
	}
	
	private void updatePicture() {
		picture.getElement().getStyle().setBackgroundImage(addUrlTag(imageList.get(currentTag).get(currentIndex)[0]));
		lblEquipment.setText(imageList.get(currentTag).get(currentIndex)[1]);
		lblSettings.setText(imageList.get(currentTag).get(currentIndex)[2]);
	}
	
	public void changeAlbum(String tag) {
		timerChangePicture.cancel();
		timerChangePicture.scheduleRepeating(ImageTimeout);
		currentIndex=-1;
		currentTag=tag;
		nextPicture();
	}
	
	private void previousPicture() {
		currentIndex=(currentIndex>0) ? (currentIndex-1)%imageList.get(currentTag).size() : imageList.get(currentTag).size()-1;
		updatePicture();
	}
	
	private void nextPicture() {
		currentIndex=(currentIndex+1)%imageList.get(currentTag).size();
		updatePicture();
	}
	
	private void updatePanelSize() {
		this.setHeight((Window.getClientHeight()-150)+"px");
		this.setWidth(Window.getClientWidth()+"px");
	}
	
	private void updatePictureSize() {
		if (btnBack!=null) {
			btnBack.setHeight((Window.getClientHeight()-170)+"px");
			btnBack.getElement().getStyle().setLineHeight((Window.getClientHeight()-150),Unit.PX);
		}
		
		if (btnNext!=null) {
			btnNext.setHeight((Window.getClientHeight()-170)+"px");
			btnNext.getElement().getStyle().setLineHeight((Window.getClientHeight()-150),Unit.PX);
		}
		
		if (picture!=null) {
			picture.setHeight((Window.getClientHeight()-170)+"px");
			picture.setWidth((Window.getClientWidth()-100)+"px");
		}
	}
	
	public String [] getAlbumList() {
		ArrayList<String> list=new ArrayList<>();
		list.addAll(imageList.keySet());
		Collections.sort(list);
		list.remove("All");
		list.add(0,"All");
		return list.toArray(new String [list.size()]);
	}
}
