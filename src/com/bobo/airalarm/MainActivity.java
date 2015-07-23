package com.bobo.airalarm;

import com.bobo.airalarm.util.ToastManager;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ContentResolver resolver= getContentResolver();
		Uri url=Uri.parse("content://com.bobo.airalarm.provider/alarm");
		ContentValues values=new ContentValues();
		values.put("active", 1);
		//Uri uri=resolver.insert(url, values);
		//ToastManager.show(this, "insert successful into " + uri.toString());
	}
}
