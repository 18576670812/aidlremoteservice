package com.whb.aidlremoteservice;

import com.whb.aidlremoteservice.aidl.BookQuery;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class BookQueryService extends Service {
	private final String TAG = "BookQueryService";
	private final String AUTHORITY = "com.hongbowang.mycontentprovider.BookProviderMetaData";
	private final Uri CONTENT_URI = 
			Uri.parse("content://" + AUTHORITY + "/books");
	private final String[] projection = {
		"_id",
		"name",
		"author"
	};
	private final String sortOrder = "modified ASC";
	private IBinder mBinder = new BookQueryBinder();
	
	private class BookQueryBinder extends BookQuery.Stub {

		@Override
		public Bundle queryBookInfoById(String id) throws RemoteException {
			// TODO Auto-generated method stub
			return queryById(id);
		}

		@Override
		public Bundle[] queryBookInfoByName(String name) throws RemoteException {
			// TODO Auto-generated method stub
			return queryByName(name);
		}

		@Override
		public Bundle[] queryBookInfoByAuthor(String author)
				throws RemoteException {
			// TODO Auto-generated method stub
			return queryByAuthor(author);
		}
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onBind()... intent: " + arg0);
		return mBinder;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onCreate()...");
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onDestroy()...");
		super.onDestroy();
	}

	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onRebind()... intent: " + intent);
		super.onRebind(intent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onStartCommand()... intent: " + intent);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onUnbind()... intent: " + intent);
		return super.onUnbind(intent);
	}
	
	public Bundle queryById(String id) {
		Log.d(TAG, "queryById()... id: " + id);
		
		if(id == null || id.equals("")) {
			Log.w(TAG, "the query condition: id is empty");
			return null;
		}
		
		Bundle bundle = new Bundle();
		Uri uri = Uri.parse("content://" + AUTHORITY + "/books/" + id);
		Cursor c = getContentResolver().query(uri, projection, null, null, sortOrder);
		
		if(c.moveToFirst()) {
			bundle.putString("BOOKID", c.getString(c.getColumnIndex("_id")));
			bundle.putString("BOOKNAME", c.getString(c.getColumnIndex("name")));
			bundle.putString("BOOKAUTHOR", c.getString(c.getColumnIndex("author")));
			
			return bundle;
		}
		
		return null;
	}
	
	public Bundle[] queryByName(String name) {
		Log.d(TAG, "queryByName()... name: " + name);
		int index = 0;
		
		if(name == null || name.equals("")) {
			Log.w(TAG, "the query condition: name is empty");
			return null;
		}
		
		Bundle[] bundle = null;
		String selection = new String("name=" + "\'" + name + "\'");

		Cursor c = getContentResolver().query(CONTENT_URI, projection, selection, null, sortOrder);
		if(c.getCount() > 0) {
			bundle = new Bundle[c.getCount()];
		}
		
		Log.d(TAG, "query by name result(Cursor): " + c + ", count record: " + c.getCount());
		if(bundle != null) {
			Log.d(TAG, "bundle: " + c + ", bundle length: " + bundle.length);
		}
		
		while(c.moveToNext()) {
			bundle[index].putString("BOOKID", c.getString(c.getColumnIndex("_id")));
			bundle[index].putString("BOOKNAME", c.getString(c.getColumnIndex("name")));
			bundle[index].putString("BOOKAUTHOR", c.getString(c.getColumnIndex("author")));
			
			index++;
		}
		
		return bundle;
	}
	
	public Bundle[] queryByAuthor(String author) {
		Log.d(TAG, "queryByAuthor()... author: " + author);
		int index = 0;
		
		if(author == null || author.equals("")) {
			Log.w(TAG, "the query condition: author is empty");
			return null;
		}
		
		Bundle[] bundle = null;
		String selection = new String("author=" + "\'" + author + "\'");

		Cursor c = getContentResolver().query(CONTENT_URI, projection, selection, null, sortOrder);
		if(c.getCount() > 0) {
			bundle = new Bundle[c.getCount()];
		}
		
		Log.d(TAG, "query by author result(Cursor): " + c + ", count record: " + c.getCount());
		if(bundle != null) {
			Log.d(TAG, "bundle: " + c + ", bundle length: " + bundle.length);
		}
		
		while(c.moveToNext()) {
			bundle[index].putString("BOOKID", c.getString(c.getColumnIndex("_id")));
			bundle[index].putString("BOOKNAME", c.getString(c.getColumnIndex("name")));
			bundle[index].putString("BOOKAUTHOR", c.getString(c.getColumnIndex("author")));
			
			index++;
		}
		Log.d(TAG, "bundle: " + c + ", index: " + index);
		return bundle;
	}
}