package com.collect.it.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class represents the database for the collect it application
 * 
 * CollectItDatabaseConstants.ITEM_IMAGE_TABLE_NAME : table that stores the data
 * of item's image if not uploaded on to server
 */
public class CollectItDatabase extends SQLiteOpenHelper {
	/**
	 * Declare class variables
	 */
	public CollectItDatabase(Context context) {
		super(context, CollectItDatabaseConstants.DATABASE_NAME, null,
				CollectItDatabaseConstants.DATABASE_VERSION);
	}

	private static SQLiteDatabase database;

	public static SQLiteDatabase getInstance(Context context) {
		if (database == null) {
			database = new CollectItDatabase(context).getWritableDatabase();
		}
		return database;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createItemImageTableQueryString = "CREATE TABLE IF NOT EXISTS "
				+ CollectItDatabaseConstants.ITEM_IMAGE_TABLE_NAME
				+ " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ CollectItDatabaseConstants.ITEM_ID + " TEXT, "
				+ CollectItDatabaseConstants.USER_ID + " TEXT, "
				+ CollectItDatabaseConstants.ITEM_IMAGE + " TEXT );";
		db.execSQL(createItemImageTableQueryString);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS "
				+ CollectItDatabaseConstants.ITEM_IMAGE_TABLE_NAME);

		// Create tables again
		onCreate(db);
	}

}
