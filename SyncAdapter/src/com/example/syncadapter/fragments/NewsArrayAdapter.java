package com.example.syncadapter.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.example.syncadapter.R;
import com.example.syncadapter.tools.Logging;

public class NewsArrayAdapter extends ArrayAdapter<NewsData> {


	private SparseArray<View> itemsViews = new SparseArray<View>();
	private Activity context;
	private List<NewsData> items;

	public NewsArrayAdapter(Activity context, List<NewsData> items) {
		super(context, R.layout.news_list_item, items);
		Logging.logEntrance();
		this.context = context;
		this.items = items;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Logging.logEntrance();

		View view = itemsViews.get(position);
		if (view == null) {
			Logging.logEntrance("Creating new view");
			NewsData m = items.get(position);

			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.news_list_item, parent, false);

			initItem(view, m);

			itemsViews.put(position, view);
		}
		return view;
	}


	private void initItem(View view, NewsData m) {
		Logging.logEntrance();
		ImageView photoView = (ImageView) view.findViewById(R.id.contacts_list_item_image); // TODO
		TextView nameView = (TextView) view.findViewById(R.id.contacts_list_item_contact_name);
		TextView emailView = (TextView) view.findViewById(R.id.contacts_list_item_contact_email);

		Uri photoUri = m.getImgUri();
		String name = m.getTitle();
		String email = m.getUrl();

		if (photoView != null && photoUri != null) {
			photoView.setImageURI(photoUri);
		}
		if (name != null) {
			nameView.setText(name);
		}
		if (email != null) {
			emailView.setText(email);
		}
	}
}
