package com.yanevskyy.y.bythewayanalitics

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import java.util.*
//
//const val PLANER_LOAD_ID = 1267
//const val DAY_TIME = 1000L * 60L * 60L * 24L

class StartUpBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
//            startLoadUsersService(context)
        }
    }

    private fun startLoadUsersService(context: Context) {
        Log.d("tag", "StartUpBootReceiver BOOT_COMPLETED")
        val latencyForStartScheduler = PreferenceManager.getDefaultSharedPreferences(context).getLong("END_START_LOADING_USERS", 0L)
                .let { timeLastRunScheduler ->
                    Log.d("tag", "onReceive::: in latencyForStartScheduler:::  timeLastRunScheduler: " + timeLastRunScheduler + "  current date: " + Calendar.getInstance().timeInMillis + DAY_TIME)
                    if (timeLastRunScheduler > Calendar.getInstance().timeInMillis + DAY_TIME || timeLastRunScheduler <= 0L) {
                        Log.d("tag", "onReceive::: in if block ")
                        1L
                    } else {
                        Log.d("tag", "onReceive::: in else block ")
                        timeLastRunScheduler + DAY_TIME - Calendar.getInstance().timeInMillis
                    }
                }

        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.schedule(JobInfo.Builder(PLANER_LOAD_ID, ComponentName(context, LoadUsersScheduler::class.java))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setMinimumLatency(latencyForStartScheduler)
                //todo 1000L * 60L * 60L * 24L - (read from preferences last start)
                .build())
    }
}
