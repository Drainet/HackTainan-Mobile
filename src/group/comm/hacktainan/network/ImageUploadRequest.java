package group.comm.hacktainan.network;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

import android.os.AsyncTask;

public class ImageUploadRequest extends AsyncTask<String, Void, String> {
	
	private LinkedList<PostPar> postParList = new LinkedList<PostPar>();
	@Override
	protected String doInBackground(String... path) {

		for (String sdPath : path) {
			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

			HttpPost httppost = new HttpPost("http://kalacoolhack.herokuapp.com/api/photos");

			File file = new File(sdPath);

			MultipartEntity mpEntity = new MultipartEntity();
			ContentBody cbFile = new FileBody(file, "image/jpeg");
			mpEntity.addPart("userfile", cbFile);
			
			for(PostPar postPar:postParList){
				try {
					mpEntity.addPart(postPar.NAME,new StringBody(postPar.VALUE));
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
			}

			httppost.setEntity(mpEntity);
			HttpResponse response;
			try {
				response = httpclient.execute(httppost);

				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
				}
				if (resEntity != null) {
					resEntity.consumeContent();
				}
				httpclient.getConnectionManager().shutdown();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return "OK";
	}      
	
	public ImageUploadRequest addPar(String name,String value){
		postParList.add(new PostPar(name, value));
		return this;
	}
	
	class PostPar{
		final String NAME;
		final String VALUE;
		public PostPar(String name,String value){
			NAME = name;
			VALUE = value;
		}
	}
}
