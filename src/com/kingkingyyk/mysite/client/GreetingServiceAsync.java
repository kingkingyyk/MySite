package com.kingkingyyk.mysite.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GreetingServiceAsync {
	void getPictureData(AsyncCallback<ArrayList<String []>> callback) throws IllegalArgumentException;
}
