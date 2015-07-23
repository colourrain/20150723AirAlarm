package com.bobo.airalarm.provider;



import com.bobo.airalarm.util.Constant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


class AlarmDBOpenHelper extends SQLiteOpenHelper{

	public AlarmDBOpenHelper(Context context, String dbName) {
		super(context, dbName, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(Constant.LOG_TAG, "creating new alarm database");
		String sql="CREATE TABLE IF NOT EXISTS alarm "
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ "active INTEGER NOT NULL,"
				+ "createtime LONG NOT NULL,"
				+ "alerttime LONG NOT NULL,"
				+ "alarmtime INTEGER NOT NULL,"
				+ "repeattype INTEGER NOT NULL,"
				+ "notitype INTEGER NOT NULL,"
				+ "snzactive INTEGER NOT NULL,"
				+ "snzduration INTEGER NOT NULL,"
				+ "snzrepeat INTEGER NOT NULL,"
				+ "snzcount INTEGER NOT NULL,"
				+ "dailybrief INTEGER NOT NULL,"
				+ "sbdactive INTEGER NOT NULL,"
				+ "sbdduration INTEGER NOT NULL,"
				+ "sbdtone INTEGER NOT NULL,"
				+ "alarmsound INTEGER NOT NULL,"
				+ "alarmtone INTEGER NOT NULL,"
				+ "volume INTEGER NOT NULL,"
				+ "sbduri INTEGER NOT NULL,"
				+ "alarmuri TEXT,name TEXT);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	
}