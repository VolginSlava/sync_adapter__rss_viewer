package com.example.syncadapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyAuthenticatorService extends Service {

	private MyAuthenticator authenticator;

	@Override
	public void onCreate() {
		authenticator = new MyAuthenticator(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return authenticator.getIBinder();
	}
}
