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
			JSONArray jsAry = json.getJSONArray("newtasks");
			
			for(int i = 0;i<jsAry.length();i++){
				JSONObject js = jsAry.getJSONObject(i);
			
				Quest quest = new Quest(
						js.getString("created_at"), 
						js.getString("desc"), 
						js.getInt("goalLikes"), 
						js.getInt("id"), 
						Double.valueOf(js.getString("lat")), 
						Double.valueOf(js.getString("lng")), 
						js.getString("title"), 
						js.getString("updated_at"));
				questList.add(quest);
			}
			

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return questList;
	}

}
