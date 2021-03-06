package group.comm.hacktainan.activity;

import java.util.LinkedList;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
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

import group.comm.hacktainan.application.HackTainanApplication;
import group.comm.hacktainan.camera.UriGenerater;
import group.comm.hacktainan.data.*;
import group.comm.hacktainan.network.RequestHelper;
import group.comm.hacktainan.R;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MapActivity extends Activity implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener
{
	
	 private static GoogleMap map;
	 private static LocationClient locationclient;
	 private static LinkedList<Quest> questList;
	 private static Location myLocation = null;
	 private static Uri fileUri;
	 private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	 private Integer mid;
	 
	 
	 @Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			 
		     if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
		         if (resultCode == RESULT_OK) {
		        	Intent intent = new Intent();
		            intent.setClass(this, CameraActivity.class);
		            intent.putExtra("fileuri", fileUri);
		            intent.putExtra("QID",questList.get(mid).QID);
		            startActivity(intent); 
		            this.finish(); 
		             
		         } else if (resultCode == RESULT_CANCELED) {
		             // User cancelled the image capture
		        	 
		         } else {
		             // Image capture failed, advise user
		        	 
		         }
		     }
		 }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		locationclient = new LocationClient(this, this, this);
		questList = new LinkedList<Quest>();
//		questlist.add(new Quest(1,"圓環頂任務","在圓環頂耍gay",23.00,120.12));
//		questlist.add(new Quest(2,"地獄級任務","蒼穹雷霆宙斯",23.00,120.14));
		
		//context = this.getApplicationContext();
		PlaceholderFragment fragment;
		
		ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setIcon(R.drawable.back_64);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
			//fragment = (PlaceholderFragment) getFragmentManager().findFragmentById(R.id.container);
		}
		
		RequestHelper.getQuestListFeed("http://kalacoolhack.herokuapp.com/all/"+HackTainanApplication.getUser().getId()
			, new Listener<LinkedList<Quest>>() {

			@Override
			public void onResponse(LinkedList<Quest> rquestList) {
				questList = rquestList;
				initialQuestMarker();
			}
		},new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				
			}
		});
	}
	
	private void initialQuestMarker(){
		
    	for(int i=0;i<questList.size();i++){
    		MarkerOptions marker = new MarkerOptions().position(new LatLng(questList.get(i).LATITUDE, questList.get(i).LONGITUDE)).title(String.valueOf(i));
    		marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
    		
    		map.addMarker(marker);
    		
    		map.setOnMarkerClickListener(new OnMarkerClickListener(){
    		    

				@Override
				public boolean onMarkerClick(Marker marker) {
					
					final Integer mmid = Integer.valueOf(marker.getTitle());
					mid = mmid;
					AlertDialog.Builder dialog = new AlertDialog.Builder(MapActivity.this);
					dialog.setTitle(questList.get(mid).TITLE);
			        dialog.setMessage(questList.get(mid).DESCRIPTION);
			        
			        //if( Math.abs(locationclient.getLastLocation().getLatitude()-questList.get(mmid).LATITUDE)<0.08 &&
			        	//	Math.abs(locationclient.getLastLocation().getLongitude()-questList.get(mmid).LONGITUDE)<0.08
			        		//){
			        	dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {  
				            public void onClick(DialogInterface dialog, int which) {  
				            	
				            	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				                fileUri = UriGenerater.getOutputMediaFileUri(1); // create a file to save the image
				                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
				                
				                // start the image capture Intent
				                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				                
				            }  
				        }); 
			        	
			        //}
			        
			        
			        
			        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			        	   public void onClick(DialogInterface dialog, int which) {
			        	    // TODO Auto-generated method stub
			        		   dialog.dismiss();
			        	   }
		        	});
			        
			        dialog.show();
					
					return true;
				}});
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
		if (id == android.R.id.home) {
			this.finish();
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
		
		Context context;
		
		

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_map, container,
					false);
			context = this.getActivity();
			
			map =  ((MapFragment)this.getFragmentManager().findFragmentById(R.id.map)).getMap();
			map.setMyLocationEnabled(true);
			Log.i("LIE", String.valueOf(map.isMyLocationEnabled()) );
			
			

		    
			
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
    

}
