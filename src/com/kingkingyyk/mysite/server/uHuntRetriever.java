package com.kingkingyyk.mysite.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class uHuntRetriever {
	
	private static int retrieveId (String username) {
		try {
			URL url = new URL("http://uhunt.felix-halim.net/api/uname2uid/"+username);
			BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
			int id=Integer.parseInt(br.readLine());
			br.close();
			return id;
		} catch (IOException e) {}
	    return -1;
	}
	
		
	private static String rankingList (int id, int ceiling, int floor) {
		if (id!=-1) {
			try {
				URL url = new URL("http://uhunt.felix-halim.net/api/ranklist/"+id+"/"+ceiling+"/"+floor);
				BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
				String s=br.readLine();
				return s;
			} catch (IOException e) {}
		}
	    return null;
	}
	
	public static String getRankingListByUsername (String name, int ceiling, int floor) {
		return rankingList(retrieveId(name),ceiling,floor);
	}
	
}
