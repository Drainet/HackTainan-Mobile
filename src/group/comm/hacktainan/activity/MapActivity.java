package group.comm.hacktainan.activity;

import java.util.LinkedList;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import group.comm.hacktainan.data.*;
import group.comm.hacktainan.R;
import group.comm.hacktainan.R.id;
import group.comm.hacktainan.R.layout;
import group.comm.hacktainan.R.menu;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MapActivity extends Activity implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener{
	
	 private static GoogleMap map;
	 private static LocationClient locationclient;
	 private static LinkedList<Quest> questlist;
	 private static Location myLocation = null;
	 
	 
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		locationclient = new LocationClient(this, this, this);
		questlist = new LinkedList<Quest>();
		questlist.add(new Quest(1,"圓環頂任務","在圓環頂耍gay",23.00,120.12));
		questlist.add(new Quest(2,"地獄級任務","蒼穹雷霆宙斯",23.00,120.14));
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}
		
		

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_map, container,
					false);
			
			map =  ((MapFragment)this.getFragmentManager().findFragmentById(R.id.map)).getMap();
			//Log.i("LIE", String.valueOf(map.isMyLocationEnabled()) );
			map.setMyLocationEnabled(true);
			Log.i("LIE", String.valueOf(map.isMyLocationEnabled()) );
			
			initialQuestMarker();

		    
			//map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10));
			//map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
			return rootView;
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		myLocation = locationclient.getLastLocation();
		
		if(myLocation!=null){
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 6));
			map.animateCamera(CameraUpdateFactory.zoomTo(11), 2000, null);
		}
		
		
		
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        locationclient.connect();
    }
    
    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
    	locationclient.disconnect();
        super.onStop();
    }
    private static void initialQuestMarker(){
    	for(int i=0;i<questlist.size();i++){
    		MarkerOptions marker = new MarkerOptions().position(new LatLng(questlist.get(i).LATITUDE, questlist.get(i).LONGITUDE)).title(questlist.get(i).NAME);
    		marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
    		map.addMarker(marker);
    		map.setOnMarkerClickListener(new OnMarkerClickListener(){
    		    

				@Override
				public boolean onMarkerClick(Marker marker) {
					// TODO Auto-generated method stub
					return false;
				}});
    	}
    	
    }

}
