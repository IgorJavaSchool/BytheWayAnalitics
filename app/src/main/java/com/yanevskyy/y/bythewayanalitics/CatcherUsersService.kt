package com.yanevskyy.y.bythewayanalitics

import android.app.IntentService
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import android.util.Log
import com.firebase.mm.myapplication.User
import com.yanevskyy.y.bythewayanalitics.LoadUsersScheduler.Companion.NOTIFICATION_ID
import com.yanevskyy.y.bythewayanalitics.repository.OnRequestedUsers


class CatcherUsersService : IntentService("CatcherUsersService") {
    private val LOG_TAG = "tag"

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, "onCreate")
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(LOG_TAG, "onHandleIntent start ")
        if (isOnline()) {
            App.INSTANCE.userRepository.requestAllUsers(object : OnRequestedUsers() {
                override fun onSuccessRequested(users: MutableList<User>) {
                    Log.d("tag", "getting users such users: " + users)
                    App.INSTANCE.dbManager.insertNowNotExistUsers(users)
                    PreferenceManager.getDefaultSharedPreferences(this@CatcherUsersService).edit().putLong("END_START_LOADING_USERS", System.currentTimeMillis()).apply()
                    (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancel(NOTIFICATION_ID)
                    LoadUsersScheduler.scheduleWithDelay(this@CatcherUsersService)
                }
            })
        }
//        FirebaseFirestore.getInstance().collection("users").get().addOnSuccessListener { task ->
//            val users = ArrayList<User>()
//            task.documents.forEach {
//                Log.d(ContentValues.TAG, it.id + " => " + it.data)
//                users.add(fillUserDao(it))
//            }
//            App.INSTANCE.dbManager.insertNowNotExistUsers(users)
//        }.addOnFailureListener({ error -> Log.w(ContentValues.TAG, "Error getting documents.", error) })
//        Log.d(LOG_TAG, "onHandleIntent end ")
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d(LOG_TAG, "onDestroy")
//        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancel(NOTIFICATION_ID)
//        PreferenceManager.getDefaultSharedPreferences(this).edit().putLong("END_START_LOADING_USERS", System.currentTimeMillis()).apply()
//        LoadUsersScheduler.scheduleWithDelay(this)
//    }


    private fun isOnline(): Boolean {
        val netInfo = (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}