package group.comm.hacktainan.network;

import group.comm.hacktainan.data.Quest;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

public class QuestListRequest extends ObjectListRequest<Quest>{

	public QuestListRequest(int method, String url,
			Listener<LinkedList<Quest>> listener, ErrorListener errorListener) {
		super(method, url, listener, errorListener);
		// TODO Auto-generated constructor stub
	}

	@Override
	LinkedList<Quest> processJSON(JSONObject json) {
		LinkedList<Quest> questList = new LinkedList<Quest>();
		try {
			JSONArray jsAry = json.getJSONArray("quests");
			
			for(int i = 0;i<jsAry.length();i++){
				JSONObject jsobj = jsAry.getJSONObject(i);
//				Quest quest = new Quest(jsobj.getInt("id"));
				
				
			}
			

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return questList;
	}

}
