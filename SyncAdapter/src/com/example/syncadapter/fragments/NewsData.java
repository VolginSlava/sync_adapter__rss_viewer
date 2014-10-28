package com.example.syncadapter.fragments;

import android.net.Uri;

import java.io.Serializable;

import com.example.syncadapter.tools.Logging;

public class NewsData implements Serializable {
	private static final long serialVersionUID = -5158009157779590079L;

	// private Uri photoUri; // to support serialization
	private String imgUri;
	private String Title;
	private String url;


	public NewsData() {
		Logging.logEntrance();
	}


	public Uri getImgUri() {
		return imgUri != null ? Uri.parse(imgUri) : null;
	}

	public void setImgUri(Uri photo) {
		this.imgUri = photo != null ? photo.toString() : null;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String name) {
		this.Title = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return String.format("[Title=%s, url=%s, uri=%s]", Title, url, imgUri);
	}
}
