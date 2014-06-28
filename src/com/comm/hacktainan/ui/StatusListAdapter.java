package com.comm.hacktainan.ui;

import group.comm.hacktainan.R;
import group.comm.hacktainan.application.HackTainanApplication;
import group.comm.hacktainan.data.Status;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.StringRequest;

import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;

public class StatusListAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private LinkedList<Status> statusList = new LinkedList<Status>();
	private StatusListAdapter statusListAdapter;

	public StatusListAdapter(final Context context, final LinkedList<Status> statusList) {
		this.statusList = statusList;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		statusListAdapter = this;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return statusList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return statusList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null || view.getTag() == null) {
			view = inflater.inflate(R.layout.status_view, null);
			final StatusViewHolder holder = new StatusViewHolder();
			holder.name = (TextView) view.findViewById(R.id.name);
			holder.title = (TextView) view.findViewById(R.id.title);

			holder.date = (TextView) view.findViewById(R.id.date);
			holder.description = (TextView) view.findViewById(R.id.description);
			holder.likeButton = (ImageView) view.findViewById(R.id.good);
			holder.profile = (ImageView) view.findViewById(R.id.profileImage);
			holder.photo = (ImageView) view.findViewById(R.id.photo);
			holder.progressbar = (ProgressBar) view.findViewById(R.id.progressBar);
			holder.goodnum = (TextView) view.findViewById(R.id.goodNumber);
			holder.position = position;
			view.setTag(holder);
		}
		final StatusViewHolder holder = (StatusViewHolder) view.getTag();

		holder.name.setText(statusList.get(position).NAME);
		holder.title.setText(statusList.get(position).TITLE);
		holder.date.setText(statusList.get(position).DATE);
		holder.description.setText(statusList.get(position).CONTENT);
		holder.likeButton.setImageResource(R.drawable.like_64);
		holder.photo.setImageResource(R.drawable.friend_64);
		holder.profile.setVisibility(View.INVISIBLE);
		holder.goodnum.setText(String.valueOf(statusList.get(position).LIKES)+"/"+String.valueOf(statusList.get(position).GOAL));
		holder.progressbar.setMax(statusList.get(position).GOAL);
		holder.progressbar.setProgress(statusList.get(position).LIKES);
		final int id = statusList.get(position).ID;
		final int pos = position;
		
		holder.photo.setVisibility((View.INVISIBLE));

		holder.likeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://kalacoolhack.herokuapp.com/api/likes",
						new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						statusList.get(pos).LIKES = Integer.valueOf(arg0);
						statusListAdapter.notifyDataSetChanged();
					}
				},new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
					}
				}){
					@Override
					protected Map<String, String> getParams() 
					{  
						Map<String, String>  params = new HashMap<String, String>();  
						params.put("uid", HackTainanApplication.getUser().getId());  
						params.put("article_id",String.valueOf(id));

						return params;  
					}
				};
				HackTainanApplication.getRequestQueue().add(stringRequest);


			}
		});


		HackTainanApplication.getImageLoader().get("http://kalacoolhack.herokuapp.com"+statusList.get(position).IMAGE, new ImageListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
			@Override
			public void onResponse(ImageContainer container, boolean arg1) {
				if(holder.position == pos){
					holder.photo.setVisibility((View.VISIBLE));
					holder.photo.setImageBitmap(container.getBitmap());
				}
			}
		});
		return view;
	}

	protected final class StatusViewHolder {
		int position;
		TextView name;
		TextView title;
		TextView date;
		TextView goodnum;
		TextView description;
		ProgressBar progressbar;
		ImageView profile;
		ImageView likeButton;
		ImageView photo;
	}

}
