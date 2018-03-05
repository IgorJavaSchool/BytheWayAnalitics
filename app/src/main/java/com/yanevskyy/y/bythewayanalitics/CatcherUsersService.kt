package com.yanevskyy.y.bythewayanalitics

import android.app.IntentService
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.ServiceCompat
import android.util.Log
import com.yanevskyy.y.bythewayanalitics.LoadUsersScheduler.Companion.NOTIFICATION_ID
import java.util.concurrent.TimeUnit
import javax.xml.datatype.DatatypeConstants.SECONDS



class CatcherUsersService : IntentService("CatcherUsersService") {
    private val LOG_TAG = "myLogs"

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, "onCreate")
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(LOG_TAG, "onHandleIntent start ")
        try {
            TimeUnit.SECONDS.sleep(4L)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        Log.d(LOG_TAG, "onHandleIntent end ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestroy")
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancel(NOTIFICATION_ID)
    }
}