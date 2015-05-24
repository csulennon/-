package com.kongderui.taskmanager.db;

import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kongderui.taskmanager.TaskManagerApp;
import com.kongderui.taskmanager.util.TMLog;

public class DBHelper extends SQLiteOpenHelper {
	private static DBHelper mDBHelper = null;

	private static final String DATABASE_TASKMANAGER = "taskmanager.db";
	private static final int DATABASE_VERSION = 1;

	private static final String SQL_CREATE_TASK = "CREATE TABLE IF NOT EXISTS task (_id INTEGER PRIMARY KEY AUTOINCREMENT,  title VARCHAR(200), content VARCHAR(500), type integer, starttime integer, endtime integer, duration integer, extra text)";
	
	private SQLiteDatabase mDatabase = null;
	private final AtomicInteger mOpenCounter = new AtomicInteger();
	
	@Override
	public SQLiteDatabase getWritableDatabase() {
		return openDatabase();
	}
	
	public DBHelper(Context context) {
		super(context, DATABASE_TASKMANAGER, null, DATABASE_VERSION);
	}

	/* ��ȡ���ݿ��������ʵ�� */
	public static synchronized DBHelper getInstance() {
		if (mDBHelper == null) {
			Context context = TaskManagerApp.getAppContext();
			mDBHelper = new DBHelper(context);
		}
		return mDBHelper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		TMLog.d("�������ݿ�");
		db.execSQL(SQL_CREATE_TASK);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

	// �����ݿ�
	public SQLiteDatabase openDatabase() {
		if (mOpenCounter.incrementAndGet() == 1) {
			try {
				mDatabase = super.getWritableDatabase();
			} catch (SQLException ex) {
				ex.printStackTrace();
				TMLog.e( "�����ݿ� " + DATABASE_TASKMANAGER + "�쳣��");
			}
		}
		return mDatabase;
	}

	// �ر����ݿ�
	public void closeDatabase() {
		int openCount = mOpenCounter.decrementAndGet();
		if (openCount == 0) {
			try {
				if (mDatabase != null) {
					mDatabase.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				TMLog.e( "�ر����ݿ� " + DATABASE_TASKMANAGER + "�쳣��");
			} finally {
				mDatabase = null;
			}
		}
	}
}
