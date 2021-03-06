package com.example.syncadapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import com.example.syncadapter.fragments.NewsData;
import com.example.syncadapter.fragments.NewsListFragment;
import com.example.syncadapter.rssparser.RssParser;
import com.example.syncadapter.rssparser.RssParser.Fields;
import com.example.syncadapter.tools.Logging;

public class HomeActivity extends Activity implements OnItemClickListener {

	private static final String ACCOUNT_NAME = "my_account";
	private static final int ACCOUNT_TYPE_ID = R.string.account_type;
	private static final String AUTHORITY = "com.example.syncadapter.provider";

	private static final URL URL;
	static {
		try {
			URL = new URL("http://www.pcworld.com/index.rss");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	private Account account;
	private NewsListFragment newsListFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		Logging.logEntrance();

		newsListFragment = (NewsListFragment) getFragmentManager().findFragmentById(R.id.news_list_fragment);
		if (newsListFragment != null) {
			newsListFragment.setOnItemClickListener(this);
		}

		// account = addNewSyncAccount(this);
		
		
		if (savedInstanceState == null) {
			new Thread() {
				@Override
				public void run() {
					try {
						RssParser p = new RssParser(URL.openConnection().getInputStream());
						final ArrayList<NewsData> ar = new ArrayList<NewsData>();
						String res;

						while ((res = p.findWithinHorizon()) != null) {
							Map<Fields, String> match = p.match();
							NewsData data = new NewsData();

							data.setTitle(match.get(RssParser.Fields.TITLE));
							data.setUrl(match.get(RssParser.Fields.LINK));

							ar.add(data);
						}
						Log.w("", "Ready");

						HomeActivity.this.runOnUiThread(new Runnable() {

							public void run() {
								newsListFragment.updateListItems(ar);
							}
						});
					} catch (IOException e) {
						e.printStackTrace();
					}
				};
			}.start();
		}
	}

	private Account addNewSyncAccount(Context context) {
		Account newAccount = new Account(ACCOUNT_NAME, getString(ACCOUNT_TYPE_ID));

		AccountManager accountManager =
		// AccountManager.get(context);
		(AccountManager) context.getSystemService(ACCOUNT_SERVICE);
		Account[] accs = accountManager.getAccounts();


		Log.i("", "accs.length = " + accs.length);
		for (Account acc : accs) {
			Log.i("", String.format("acc.name = %s; acc.type = %s", acc.name, acc.type));
		}


		boolean added = accountManager.addAccountExplicitly(newAccount, null, new Bundle());

		if (!added) {
			Logging.logEntrance("Can't add account", new Throwable());
		}
		return added ? newAccount : null;
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		NewsData data = (NewsData) parent.getItemAtPosition(position);
		Logging.logEntrance(data.toString());

		Intent urlIntent = getUrlIntent(data);

		if (urlIntent != null) {
			startActivity(Intent.createChooser(urlIntent, "sdlfkghl! sdful D dlfu?"));
		} else {
			String msg = "no url";
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}
	}

	private Intent getUrlIntent(NewsData data) {
		Logging.logEntrance();
		if (data == null || data.getUrl() == null) {
			return null;
		}
		return new Intent(Intent.ACTION_VIEW, Uri.parse(data.getUrl()));
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
