package group.comm.hacktainan.network;

import java.util.LinkedList;

import com.android.volley.Request;
import com.android.volley.Response;




import group.comm.hacktainan.application.HackTainanApplication;
import group.comm.hacktainan.data.Status;

public class RequestHelper {

	public static void getStatusListFeed(String Url,final Response.Listener<LinkedList<Status>> listener, final Response.ErrorListener errorListener){
		final StatusListRequest request = 
				new StatusListRequest(Request.Method.GET,
						Url, listener,
						errorListener);
		HackTainanApplication.getRequestQueue().add(request);

//		final QuestListRequest qRequest = new QuestListRequest(Request.Method.GET,
//				Url, listener,
//				errorListener);
//		HackTainanApplication.getRequestQueue().add(request);
	}

}
