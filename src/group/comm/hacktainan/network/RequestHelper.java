package group.comm.hacktainan.network;

import java.util.LinkedList;

import com.android.volley.Request;
import com.android.volley.Response;




import com.android.volley.Response.Listener;

import group.comm.hacktainan.application.HackTainanApplication;
import group.comm.hacktainan.data.Quest;
import group.comm.hacktainan.data.Status;

public class RequestHelper {

	public static void getStatusListFeed(String Url,final Response.Listener<LinkedList<Status>> listener, final Response.ErrorListener errorListener){
		final StatusListRequest request = 
				new StatusListRequest(Request.Method.GET,
						Url, listener,
						errorListener);
		HackTainanApplication.getRequestQueue().add(request);

	}
	
	public static void getQuestListFeed(String Url,final Listener<LinkedList<Quest>> listener, final Response.ErrorListener errorListener){
		final QuestListRequest request = 
				new QuestListRequest(Request.Method.GET,
						Url, listener,
						errorListener);
		HackTainanApplication.getRequestQueue().add(request);

	}
	

}
