package com.bobo.airalarm.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

public class AlarmContentProvider extends ContentProvider {

	public static final String DB_NAME="alarm.db";
	public static final String TABLE_NAME="alarm";
	public static final Uri CONTENT_URI = Uri.parse("content://com.bobo.airalarm.provider/alarm");
	private AlarmDBOpenHelper mOpenHelper;
	@Override
	public boolean onCreate() {
		mOpenHelper=new AlarmDBOpenHelper(getContext(), DB_NAME);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String whereClause,
			String[] whereArgs, String sortOrder) {
		SqlArguments localSqlArguments=new SqlArguments(uri,whereClause,whereArgs);
		Cursor cursor=mOpenHelper.getWritableDatabase().query(localSqlArguments.table, projection, localSqlArguments.where, localSqlArguments.args, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		SqlArguments localSqlArguments = new SqlArguments(uri, null, null);
		if (TextUtils.isEmpty(localSqlArguments.where))
			return "vnd.android.cursor.dir/" + localSqlArguments.table;
		return "vnd.android.cursor.item/" + localSqlArguments.table;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SqlArguments localSqlArguments=new SqlArguments(uri);
		Long id=mOpenHelper.getWritableDatabase().insert(localSqlArguments.table, null, values);
		Uri localUri= ContentUris.withAppendedId(uri, id);
		sendNotifyAll(localUri);
		return localUri;
	}

	

	@Override
	public int delete(Uri uri, String whereClause, String[] whereArgs) {
		SqlArguments localSqlArguments=new SqlArguments(uri,whereClause,whereArgs);
		int i=mOpenHelper.getWritableDatabase().delete(TABLE_NAME, localSqlArguments.where, localSqlArguments.args);
		if(i>0) sendNotifyAll(uri);
		return i;
	}

	@Override
	public int update(Uri uri, ContentValues values, String whereClause,
			String[] whereArgs) {
		SqlArguments localSqlArguments=new SqlArguments(uri,whereClause,whereArgs);
		int i=mOpenHelper.getWritableDatabase().update(localSqlArguments.table, values, localSqlArguments.where, localSqlArguments.args);
		if(i>0) sendNotifyAll(uri);
		return i;
	}
	
	private void sendNotifyAll(Uri localUri) {
		getContext().getContentResolver().notifyChange(localUri, null);
	}
	
	static class SqlArguments {
		public final String[] args;
		public final String table;
		public final String where;

		SqlArguments(Uri paramUri) {
			if (paramUri.getPathSegments().size() == 1) {
				this.table = ((String) paramUri.getPathSegments().get(0));
				this.where = null;
				this.args = null;
				return;
			}
			throw new IllegalArgumentException("Invalid URI: " + paramUri);
		}

		SqlArguments(Uri paramUri, String where,String[] args) {
			if (paramUri.getPathSegments().size() == 1) {
				this.table = ((String) paramUri.getPathSegments().get(0));
				this.where = where;
				this.args = args;
				return;
			}
			if (paramUri.getPathSegments().size() != 2)
				throw new IllegalArgumentException("Invalid URI: " + paramUri);
			if (!TextUtils.isEmpty(where))
				throw new UnsupportedOperationException(
						"WHERE clause not supported: " + paramUri);
			this.table = ((String) paramUri.getPathSegments().get(0));
			this.where = ("_id=" + ContentUris.parseId(paramUri));
			this.args = null;
		}
	}


}
