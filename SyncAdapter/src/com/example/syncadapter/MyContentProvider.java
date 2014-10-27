package com.example.syncadapter;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.syncadapter.tools.Logging;

public class MyContentProvider extends ContentProvider {

	@Override
	public boolean onCreate() {
		Logging.logEntrance();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Logging.logEntrance();
		return null;
	}

	@Override
	public String getType(Uri uri) {
		Logging.logEntrance();
		return new String();
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Logging.logEntrance();
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		Logging.logEntrance();
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		Logging.logEntrance();
		return 0;
	}

}
