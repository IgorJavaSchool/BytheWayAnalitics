package com.yanevskyy.y.bythewayanalitics

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.AsyncTask
import com.firebase.mm.myapplication.User

private const val TABLE_NAME = "users"
private const val COLUMN_USER_ID = "user_id"
private const val COLUMN_ITS_DATE = "its_date"

class DbManager(context: Context) : SQLiteOpenHelper(context, "UsersDb.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
                "create table " + TABLE_NAME + " " +
                        "(" + COLUMN_USER_ID + " text primary key, " + COLUMN_ITS_DATE + " text" + " )"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun insertNowNotExistUsers(users: List<User>) {
        val db = this.writableDatabase
        db.beginTransaction()
        try {
            val currentTime = System.currentTimeMillis()
            for (user in users) {
                val contentValues = ContentValues()
                contentValues.put(COLUMN_ITS_DATE, currentTime)
                contentValues.put(COLUMN_USER_ID, user.id)
                db.insert(TABLE_NAME, null, contentValues)
            }
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.endTransaction()
        }
        db.close()
    }

    fun installDatesInUsers(users: MutableList<User>, listener: OnInstallDates) {
        InstallerDatesInUsers(users, this, listener).execute()
    }
}

private class InstallerDatesInUsers(val users: MutableList<User>, val dbManager: DbManager, val listener: OnInstallDates) : AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg p0: Void?): Void? {
        val dates = HashMap<String, Long>()
        val allData = dbManager.writableDatabase.query(TABLE_NAME, null, null, null, null, null, COLUMN_ITS_DATE)
        val idIndex = allData.getColumnIndex(COLUMN_USER_ID)
        val dateIndex = allData.getColumnIndex(COLUMN_ITS_DATE)

        while (allData.moveToNext())
            dates[allData.getString(idIndex)] = allData.getString(dateIndex).toLong()
        allData.close()
        dbManager.writableDatabase.close()
        for (user in users)
            dates[user.id]?.let { user.catchingDate = it }
        return null
    }

    override fun onPostExecute(void: Void?) {
        super.onPostExecute(void)
        listener.onInstalled()
    }
}

interface OnInstallDates {
    fun onInstalled()
}