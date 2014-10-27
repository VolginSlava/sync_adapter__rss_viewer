package com.example.syncadapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.syncadapter.tools.Logging;

public class HomeActivity extends Activity {

	private static final String ACCOUNT_NAME = "my_account";
	private static final int ACCOUNT_TYPE_ID = R.string.account_type;
	private static final String AUTHORITY = "com.example.syncadapter.provider";

	private Account account;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		Logging.logEntrance();

		account = addNewSyncAccount(this);
	}

	private Account addNewSyncAccount(Context context) {
		Account newAccount = new Account(ACCOUNT_NAME, getString(ACCOUNT_TYPE_ID));

		AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);
		boolean added = accountManager.addAccountExplicitly(newAccount, null, null);

		if (!added) {
			Logging.logEntrance("Can't add account", new Throwable());
		}
		return added ? newAccount : null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
