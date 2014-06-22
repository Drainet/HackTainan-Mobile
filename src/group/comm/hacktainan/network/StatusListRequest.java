package group.comm.hacktainan.network;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import group.comm.hacktainan.data.Status;

public class StatusListRequest extends ObjectListRequest<Status>{

	public StatusListRequest(int method, String url,
			Listener<LinkedList<Status>> listener, ErrorListener errorListener) {
		super(method, url, listener, errorListener);
	}

	@Override
	LinkedList<Status> processJSON(JSONObject jsonObject) {
		LinkedList<Status> statusList = new LinkedList<Status>();
		try {
			JSONArray jsary = jsonObject.getJSONArray("ary");
			for(int i = 0;i<jsary.length();i++){
				JSONObject js;

				js = (JSONObject) jsary.get(i);
				Status status = new Status(
						js.getString("name"),
						js.getString("title"),
						js.getString("date"),
						js.getString("description"),
						js.getString("url"),
						js.getInt("progress"),
						js.getInt("goodNumber")
						);
				statusList.add(status);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}


		return statusList;
	}

}
