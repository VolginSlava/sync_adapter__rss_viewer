package com.example.syncadapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MySyncService extends Service {

	private static MySyncAdapter syncAdapter;
	private static final Object syncAdapterLock = new Object();

	@Override
	public void onCreate() {
		synchronized (syncAdapterLock) {
			if (syncAdapter == null) {
				syncAdapter = new MySyncAdapter(getApplicationContext(), true);
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return syncAdapter.getSyncAdapterBinder();
	}

}
