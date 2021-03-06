package group.comm.hacktainan.activity;


import group.comm.hacktainan.R;
import group.comm.hacktainan.application.HackTainanApplication;
import group.comm.hacktainan.data.Status;
import group.comm.hacktainan.mywidget.RoundedImageView;
import group.comm.hacktainan.network.RequestHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.comm.hacktainan.ui.StatusListAdapter;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

import android.view.View.OnClickListener;
import android.support.v4.widget.DrawerLayout;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ResourceAsColor")public class MainActivity extends Activity
implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
	 */
	public static RoundedImageView image;
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		mNavigationDrawerFragment = (NavigationDrawerFragment)
				getFragmentManager().findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		((DrawerLayout) findViewById(R.id.drawer_layout)).setScrimColor(android.R.color.transparent);
		mNavigationDrawerFragment.setUp(
				R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));


		Session.openActiveSession(this, true, new Session.StatusCallback() {




			// callback when session changes state
			@Override
			public void call(Session session, SessionState state, Exception exception) {
				if (session.isOpened()) {

					// make request to the /me API
					Request.newMeRequest(session, new Request.GraphUserCallback() {

						// callback after Graph API response with user object
						@Override
						public void onCompleted(final GraphUser user, Response response) {
							Log.i("transmitt", "ininin");
							if (user != null) {
								if(HackTainanApplication.getUser()==null || !HackTainanApplication.getUser().equals(user)){
									HackTainanApplication.setUser(user);
									
									HackTainanApplication.getRequestQueue().add(new StringRequest(com.android.volley.Request.Method.POST,
											"http://kalacoolhack.herokuapp.com/api/login",
											new Listener<String>(){

										@Override
										public void onResponse(String arg0) {

										}},
										new ErrorListener() {

											@Override
											public void onErrorResponse(VolleyError arg0) {

											}
										}){
										@Override
										protected java.util.Map<String,String> getParams() throws com.android.volley.AuthFailureError {
											Map<String,String> map = new HashMap<String, String>();
											//										SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
											map.put("uid", String.valueOf(user.getId()));
											map.put("name", user.getName());
											map.put("token", Session.getActiveSession().getAccessToken());
											map.put("provider","facebook");
											//										map.put("expiration", sdFormat.format(Session.getActiveSession().getExpirationDate()));
											return map;
										};
									});
								}
								
								
								
								HackTainanApplication.getImageLoader().get("http://graph.facebook.com/"+user.getId()+"/picture?type=large", new ImageListener() {
									
									@Override
									public void onErrorResponse(VolleyError arg0) {
										// TODO Auto-generated method stub
										
									}
									
									@Override
									public void onResponse(ImageContainer arg0, boolean arg1) {
										//image.setImageBitmap(arg0.getBitmap());
										
									}
								});
							}}
					}).executeAsync();
				}
			}
		});

        
        
      
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
    	
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
        
        
        
       
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        ViewGroup view = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.actionbar_view, null);
        actionBar.setCustomView(view);
        ImageView image = (ImageView) view.findViewById(R.id.imageView1);
        image.setImageResource(R.drawable.starquest_64);
        actionBar.setIcon(android.R.color.transparent);
        OnClickListener clickListener = new OnClickListener() {
            public void onClick(View v) {
            			//Toast.(this, "Example action.", Toast.LENGTH_SHORT).show();
            			//Toast.makeText(MainActivity.this, "Example action.", Toast.LENGTH_SHORT).show();
            			if(HackTainanApplication.getUser()!=null){
			            	Intent intent = new Intent();
			            	intent.setClass(MainActivity.this, MapActivity.class);
			            	startActivity(intent);
            			}else{
            				Toast.makeText(MainActivity.this, "FB�n�J����", Toast.LENGTH_SHORT).show();
            			}
		            	//MainActivity.this.finish(); 
                   }
        };
        
        image.setOnClickListener(clickListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static PlaceholderFragment instance;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            instance = fragment;
            return fragment;
        }
        
        public static PlaceholderFragment getInstance(){
        	return instance;
        }

		
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
        	
        	LinkedList<Status> statusList = new LinkedList<Status>();

        	StatusListAdapter adapter = new StatusListAdapter(getActivity(),statusList);
        	View rootView;
        	if(getArguments().getInt(ARG_SECTION_NUMBER)==1){
        		rootView = inflater.inflate(R.layout.homepage, container, false);
        		image =(RoundedImageView) rootView.findViewById(R.id.imageView1);
        		//image.setImageResource(R.drawable.back_64);
        		//image.setImageDrawable(R.drawable.back_64);
        	}else{
        		rootView = inflater.inflate(R.layout.fragment_main, container, false);
        	}
            final Context context =  this.getActivity();
            final ListView listview = (ListView) rootView.findViewById(R.id.listView1);
            switch(getArguments().getInt(ARG_SECTION_NUMBER)){
            
            case 2:
            	if(HackTainanApplication.getUser()!=null)
            	 RequestHelper.getStatusListFeed("http://kalacoolhack.herokuapp.com/all/"+HackTainanApplication.getUser().getId(), new Listener<LinkedList<Status>>() {

         			@Override
         			public void onResponse(LinkedList<Status> arg0) {
         				// TODO Auto-generated method stub
         				StatusListAdapter adapter = new StatusListAdapter(context ,arg0);
         				listview.setAdapter(adapter);
         			}
         		}, new ErrorListener() {

         			@Override
         			public void onErrorResponse(VolleyError arg0) {
         				// TODO Auto-generated method stub
         				
         			}
         		});
            	
                break;
            case 3:
            	if(HackTainanApplication.getUser()!=null)
            	 RequestHelper.getStatusListFeed("http://kalacoolhack.herokuapp.com/all/stranger/"+HackTainanApplication.getUser().getId(), new Listener<LinkedList<Status>>() {

         			@Override
         			public void onResponse(LinkedList<Status> arg0) {
         				// TODO Auto-generated method stub
         				StatusListAdapter adapter = new StatusListAdapter(context ,arg0);
         				listview.setAdapter(adapter);
         				
         			}
         		}, new ErrorListener() {

         			@Override
         			public void onErrorResponse(VolleyError arg0) {
         				// TODO Auto-generated method stub
         				
         			}
         		});
            	
                break;
            case 4:
                
                break;
            		
            	
            }
            
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
        

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
    
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}


}
