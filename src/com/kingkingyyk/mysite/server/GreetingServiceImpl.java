package com.kingkingyyk.mysite.server;

import com.kingkingyyk.mysite.client.GreetingService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	private static String ListURL="https://docs.google.com/spreadsheets/u/0/d/1i99AZKKaP3wiscWFqCZtXzG_vs_aqb2M-j0SVlIgLIs/export?format=tsv";
	
	public ArrayList<String []> getPictureData() throws IllegalArgumentException {
		ArrayList<String []> list=new ArrayList<>();
		try {
			URL u=new URL(ListURL);
			BufferedReader br=new BufferedReader(new InputStreamReader(u.openStream()));

			String s;
			while ((s=br.readLine())!=null) list.add(s.split("\\t"));
			
			br.close();
		} catch (Exception e) {}
		return list;
	}

	public String getUVARankList (String username) throws IllegalArgumentException {
		return uHuntRetriever.getRankingListByUsername(username, 5, 5);
	}
	
}
