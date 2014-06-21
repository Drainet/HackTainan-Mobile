package group.comm.hacktainan.application;


import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.facebook.model.GraphUser;

public class HackTainanApplication extends Application{
	private static RequestQueue requestQueue;
	private static ImageLoader imageLoader;
	private static String userId;
	private static GraphUser user;
	
	@Override
	public void onCreate() {
		super.onCreate();
		requestQueue = Volley.newRequestQueue(getApplicationContext());
		imageLoader = new ImageLoader(Volley.newRequestQueue(getApplicationContext()), new BitmapLruCache());
		imageLoader.setBatchedResponseDelay(0);
		
	}

	public static RequestQueue getRequestQueue() {
		return requestQueue;
	}
	
	public static ImageLoader getImageLoader()	 {
		return imageLoader;
	}

	public static String getUserId() {
		return userId;
	}

	public static void setUserId(String userId) {
		HackTainanApplication.userId = userId;
	}

	public static GraphUser getUser() {
		return user;
	}

	public static void setUser(GraphUser user) {
		HackTainanApplication.user = user;
	}

}
