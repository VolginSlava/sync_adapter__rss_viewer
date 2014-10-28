package com.example.syncadapter.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;

import com.example.syncadapter.R;
import com.example.syncadapter.tools.Logging;

public class NewsListFragment extends Fragment {

	private static final String LIST_ITEMS_KEY = "listItems";

	public static NewsListFragment newInstnce(List<NewsData> data) {
		Logging.logEntrance();

		NewsListFragment list = new NewsListFragment();

		if (data != null) {
			Bundle args = new Bundle();
			args.putSerializable(LIST_ITEMS_KEY, (Serializable) data);
			list.setArguments(args);
		}
		return list;
	}
	
	
	private ListView listView;
	private List<NewsData> listItems;
	private OnItemClickListener listener;

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Logging.logEntrance();
		listView = (ListView) inflater.inflate(R.layout.news_list, container, false);

		if (savedInstanceState != null) {
			listItems = (List<NewsData>) savedInstanceState.getSerializable(LIST_ITEMS_KEY);
		}
		if (listItems == null && getArguments() != null) {
			listItems = (List<NewsData>) getArguments().getSerializable(LIST_ITEMS_KEY);
		}
		if (listItems != null) {
			updateListItems(listItems);
		}

		return listView;
	}

	public void updateListItems(List<NewsData> items) {
		Logging.logEntrance();
		listView.setAdapter(getAdapter(items));
		listItems = items;
	}

	private NewsArrayAdapter getAdapter(List<NewsData> items) {
		Logging.logEntrance();
		return new NewsArrayAdapter(getActivity(), items);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Logging.logEntrance();
		outState.putSerializable(LIST_ITEMS_KEY, (Serializable) listItems);
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.listener = listener;
		if (listener != null) {
			if (listView != null) {
				listView.setOnItemClickListener(listener);
			}
		}
	}
}
