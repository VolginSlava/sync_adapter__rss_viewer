package com.example.syncadapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.example.syncadapter.tools.Logging;

public class MySyncAdapter extends AbstractThreadedSyncAdapter {

	private ContentResolver contentResolver;

	public MySyncAdapter(Context context, boolean autoInitialize) {
		this(context, autoInitialize, false);
		Logging.logEntrance();
	}

	public MySyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
		super(context, autoInitialize, allowParallelSyncs);
		Logging.logEntrance();

		contentResolver = context.getContentResolver();
	}

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
		Logging.logEntrance("!!! sync !!!");



	}

}
