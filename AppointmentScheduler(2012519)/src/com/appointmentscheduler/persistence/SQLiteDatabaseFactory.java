package com.appointmentscheduler.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class SQLiteDatabaseFactory extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "appointmentDatabase.db";
	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_NAME = "appointment";
	public static final String _ID = BaseColumns._ID;
	public static final String DATE = "date";
	public static final String TITLE = "title";
	public static final String TIME = "time";
	public static final String DETAILS = "datails";
	Context current_context;
	
	public SQLiteDatabaseFactory(Context context) {
		  super(context, DATABASE_NAME, null, DATABASE_VERSION);
		  current_context = context;
		}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql =
		    "CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
		      + DATE + " TEXT NOT NULL, " + TITLE + " TEXT NOT NULL, " + TIME + " TEXT NOT NULL," + DETAILS + " TEXT NOT NULL" + ");";

		  db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	public String insert(String date,String title,String time, String details) {
		  
		  SQLiteDatabase rdb = this.getReadableDatabase();  
		  String sql = "SELECT * FROM " + TABLE_NAME + " WHERE "+ TITLE + "= '" + title +"' AND "+ DATE + "= '" + date + "'";
		  Cursor c = rdb.rawQuery(sql,null);
		  if(c.getCount()==0){
			  
			  SQLiteDatabase wdb = this.getWritableDatabase();
			  ContentValues values = new ContentValues();
		  values.put(DATE, date);
		  values.put(TITLE, title);
		  values.put(TIME, time);
		  values.put(DETAILS, details);
		  

		  wdb.insertOrThrow(TABLE_NAME, null, values);
		  return "success";
		  }
		  else{
			  return "title exists";
			 
		  }

		}
	
	public void deleteAll(String date){
		SQLiteDatabase wdb = this.getWritableDatabase();
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE "+ DATE + "= '" + date + "'";
		wdb.execSQL(sql);
		 
	}
	
	public Cursor select(String date){
		  SQLiteDatabase rdb = this.getReadableDatabase();  
		  String sql = "SELECT * FROM " + TABLE_NAME + " WHERE "+ DATE + "= '" + date + "'" + " ORDER BY " + TIME+ " ASC";
		  Cursor c = rdb.rawQuery(sql,null);

		  return c;
		  
	}
	
	public void deleteRow(int id){
		SQLiteDatabase wdb = this.getWritableDatabase();
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE "+ _ID + "= '" + id + "'";
		wdb.execSQL(sql);
		  
	}
	
	public String update(int id,String date,String title,String time, String details){
		SQLiteDatabase rdb = this.getReadableDatabase();  
		  String sql = "SELECT * FROM " + TABLE_NAME + " WHERE "+ TITLE + "= '" + title +"' AND "+ DATE + "= '" + date + "'";
		  Cursor c = rdb.rawQuery(sql,null);
		    SQLiteDatabase wdb = this.getWritableDatabase();
			  ContentValues values = new ContentValues();
			  values.put(TITLE, title);
			  values.put(TIME, time);
			  values.put(DETAILS, details);
			  wdb.update(TABLE_NAME, values, _ID + "= " + id, null);
				return "success";
		 		
	}
	
	public void updateDate(int id, String date){
		SQLiteDatabase wdb = this.getWritableDatabase();
		  ContentValues values = new ContentValues();
		  values.put(DATE, date);
		  wdb.update(TABLE_NAME, values, _ID + "= " + id, null);
	}
	
	public Cursor search(String date){
		SQLiteDatabase rdb = this.getReadableDatabase();
		
		  String sql = "SELECT * FROM " + TABLE_NAME + " WHERE strftime('%j',"+DATE+")>= strftime('%j','now')";

		  Cursor c = rdb.rawQuery(sql,null);

		  return c;
	}
	
	public Cursor searchAllApointments(){
		SQLiteDatabase rdb = this.getReadableDatabase();
		
		  String sql = "SELECT * FROM " + TABLE_NAME ;

		  Cursor c = rdb.rawQuery(sql,null);

		  return c;
	}


}
