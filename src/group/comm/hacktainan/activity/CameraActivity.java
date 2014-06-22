package group.comm.hacktainan.activity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;

import group.comm.hacktainan.R;
import group.comm.hacktainan.R.id;
import group.comm.hacktainan.R.layout;
import group.comm.hacktainan.R.menu;
import group.comm.hacktainan.application.HackTainanApplication;
import group.comm.hacktainan.network.ImageUploadRequest;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class CameraActivity extends Activity {

	private static Uri fileUri;
	private static Integer qid;
	static private ProgressBar progressbar;
	static private EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setIcon(R.drawable.back_64);
		fileUri=(Uri) this.getIntent().getExtras().get("fileuri");
		qid= (Integer)this.getIntent().getExtras().get("QID");

		//Toast.makeText(this, fileUri.toString(), Toast.LENGTH_SHORT).show();
		//ViewGroup view = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.cambar_view, null);
		//actionBar.setCustomView(view);
		//        Button buttonA = (Button) view.findViewById(R.id.button1);
		//        buttonA.setOnClickListener(new OnClickListener() {
		//            public void onClick(View v) {
		//            			//Toast.(this, "Example action.", Toast.LENGTH_SHORT).show();
		//            			//Toast.makeText(MainActivity.this, "Example action.", Toast.LENGTH_SHORT).show();
		////		            	Intent intent = new Intent();
		////		            	intent.setClass(CameraActivity.this, MainActivity.class);
		////		            	startActivity(intent); 
		//		            	CameraActivity.this.finish(); 
		//                   }
		//        });



		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera, menu);


		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_confirm) {
			progressbar.setVisibility(View.VISIBLE);
			String description = "Nice Day";
			
			if(editText!=null){
				description =  editText.getText().toString();
			}
			
				
			
			
			ImageUploadRequest iu = new ImageUploadRequest()
			.addPar("uid", HackTainanApplication.getUser().getId())
			.addPar("content",description)
			.addPar("task_id",String.valueOf(qid));
			
			

			try{
				iu.execute(fileUri.getPath()).get();
			}catch(InterruptedException e){

			}catch(ExecutionException e){

			}
			this.finish();
			return true;
		}
		else if (id == android.R.id.home) {
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

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_camera,
					container, false);
			

			ImageView image = (ImageView) rootView.findViewById(R.id.imageView1);
			editText = (EditText)rootView.findViewById(R.id.editText1);
			
			progressbar = (ProgressBar) rootView.findViewById(R.id.progressBar1);
			progressbar.setVisibility(View.INVISIBLE);
			
			
			if(fileUri!=null){
				image.setImageURI(fileUri);
			}

			return rootView;
		}
	}

}
