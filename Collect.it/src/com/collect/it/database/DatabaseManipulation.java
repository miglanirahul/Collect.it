package com.collect.it.database;

import com.google.android.gms.internal.dw;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * This class used for the manipulation of data with SQLite database
 */
public class DatabaseManipulation {

	/**
	 * Functionality to retrieve data from local database
	 * 
	 * @param table
	 *            name
	 * @param column
	 *            (s) array
	 * @param where
	 *            condition
	 * @param group
	 *            by clause string
	 * @param order
	 *            by clause string
	 * @param dWrite
	 *            getWritableDatabase() object of database helper class that
	 *            will help in DML queries
	 * 
	 * @return cursor
	 */
	public static Cursor findValuesFromLocalDatabase(String tableName,
			String[] columns, String whereCondition, String groupBy,
			String orderBy, SQLiteDatabase dWrite) {
		Cursor cursor = null;
		try {
			// cursor = dbObject.query(true,tableName, columns, whereCondition
			// ,null, groupBy, null, orderBy,null);
			cursor = dWrite.query(tableName, columns, whereCondition, null,
					groupBy, null, orderBy);
		} catch (Exception e) {
			// Log.e("QUERY_FUNCTION_CLASS", "Error: "+e);
			e.printStackTrace();
		}
		if (cursor != null)
			return cursor;
		else
			return null;
	}

	/**
	 * Functionality to insert in to local db
	 * 
	 * @param table
	 *            name
	 * @param table
	 *            's column names array
	 * @param table
	 *            's values for the correspondence table column array
	 * @param dWrite
	 *            getWritableDatabase() object of database helper class that
	 *            will help in DML queries
	 * 
	 * @return inserted last row number
	 */
	public static long insertData(String tableName, String[] KeyArr, String[] valuesArr, SQLiteDatabase dWrite) {
		if ((valuesArr != null) && (valuesArr.length > 0)) {
			ContentValues values = new ContentValues();

			for (int i = 0; i < valuesArr.length; i++) {
				values.put(KeyArr[i], valuesArr[i]);
			}
			return dWrite.insert(tableName, null, values);
		} else
			return 0;
	}

	/**
	 * Functionality to update table
	 * 
	 * @param table
	 *            name
	 * @param table
	 *            's column names array
	 * @param table
	 *            's values for the correspondence table column array
	 * @param where
	 *            condition
	 * @param dWrite
	 *            getWritableDatabase() object of database helper class that
	 *            will help in DML queries
	 * 
	 * @return inserted last row number
	 */
	public static int updateData(String tableName, String[] KeyArr,
			String[] valuesArr, String where, SQLiteDatabase dWrite) {
		if ((valuesArr != null) && (valuesArr.length > 0)) {
			ContentValues values = new ContentValues();

			for (int i = 0; i < valuesArr.length; i++) {
				values.put(KeyArr[i], valuesArr[i]);
			}
			return dWrite.update(tableName, values, where, null);
		} else
			return 0;
	}

	/**
	 * Functionality to delete table's contents
	 * 
	 * @param tableName
	 *            name of table that need to traverse
	 * @param whereCondition
	 *            query condition
	 * @param dWrite
	 *            getWritableDatabase() object of database helper class that
	 *            will help in DML queries
	 * 
	 * @return deleted row integer value
	 */
	public static int deleteRows(String tableName, String whereCondition,
			SQLiteDatabase dWrite) {
		int deleted = dWrite.delete(tableName, whereCondition, null);

		return deleted;
	}

}
