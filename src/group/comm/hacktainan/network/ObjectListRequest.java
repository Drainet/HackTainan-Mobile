package group.comm.hacktainan.network;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public abstract class ObjectListRequest<T> extends Request<LinkedList<T>>{
	private final Map<String, String> headers;
	private final Listener<LinkedList<T>> listener;
	private String charset;
	
	public ObjectListRequest(int method, String url,Listener<LinkedList<T>>  listener, ErrorListener errorListener) {
		this(method, url, null, listener, errorListener);
	}

	public ObjectListRequest(int method, String url, Map<String, String> headers, Listener<LinkedList<T>> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.headers = headers;
		this.listener = listener;
	}
	
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers != null ? headers : super.getHeaders();
	}

	@Override
	protected void deliverResponse(LinkedList<T> response) {
		listener.onResponse(response);
		
	}

	@Override
	protected Response<LinkedList<T>> parseNetworkResponse(NetworkResponse response) {
		String data;
		try {
			charset = response.headers.get("Content-Type");
			if(charset!=null){
				if(charset.contains("Charset=")){
					charset = charset.substring(charset.indexOf("Charset=",0)+8,charset.length());
					if(charset.contains(";"))
						charset = charset.substring(0, charset.indexOf(";"));
				}else
					charset = "UTF-8";
				data = new String(response.data,charset);
			}else
				data = new String(response.data,"UTF-8");
			JSONObject json = new JSONObject(data);
			JSONArray jsary =json.getJSONArray("Ary");
			LinkedList<T> tList = processJSON(jsary);
			
			
			return Response.success(tList, HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (Exception e) {
			return Response.error(new VolleyError(e.getMessage()));
		}
	}

	abstract LinkedList<T> processJSON(JSONArray jsary);

}
