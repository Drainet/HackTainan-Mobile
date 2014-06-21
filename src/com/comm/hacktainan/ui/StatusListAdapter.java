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
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null || view.getTag() == null) {
			view = inflater.inflate(R.layout.status_view, null);
			final StatusViewHolder holder = new StatusViewHolder();
//			holder.name = (TextView) view.findViewById(R.id.rss_title);

			view.setTag(holder);
		}
		final StatusViewHolder holder = (StatusViewHolder) view.getTag();
		return null;
	}
	
	protected final class StatusViewHolder {
		TextView name;
		TextView date;
		TextView description;
		ImageView profile;
		ImageView likeButton;
	}

}
