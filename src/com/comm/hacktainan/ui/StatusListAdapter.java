package com.comm.hacktainan.ui;

import group.comm.hacktainan.R;
import group.comm.hacktainan.data.Status;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;

public class StatusListAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private LinkedList<Status> statusList = new LinkedList<Status>();
	
	public StatusListAdapter(final Context context, final LinkedList<Status> statusList) {
		this.statusList = statusList;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			view.setTag(holder);
		}
		final StatusViewHolder holder = (StatusViewHolder) view.getTag();
		
		holder.name.setText(statusList.get(position).NAME);
		holder.title.setText(statusList.get(position).TITLE);
		holder.date.setText(statusList.get(position).DATE);
		holder.description.setText(statusList.get(position).DESCRIPTION);
		holder.likeButton.setImageResource(R.drawable.like_64);
		holder.photo.setImageResource(R.drawable.friend_64);
		holder.profile.setVisibility(View.INVISIBLE);
		holder.goodnum.setText(String.valueOf(statusList.get(position).PROGRESS)+"/"+String.valueOf(statusList.get(position).GOOD_NUMBER));
		holder.progressbar.setMax(statusList.get(position).GOOD_NUMBER);
		holder.progressbar.setProgress(statusList.get(position).PROGRESS);
		return view;
	}
	
	protected final class StatusViewHolder {
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
