package com.yanevskyy.y.bythewayanalitics

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager

class StartUpBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            startLoadUsersService(context)
        }
    }

    private fun startLoadUsersService(context: Context) {
        val latencyForStartScheduler = PreferenceManager.getDefaultSharedPreferences(context).getLong("END_START_LOADING_USERS", 0L)
                .let { timeLastRunScheduler ->
                    var planeDelayStartCatching = (timeLastRunScheduler + DAY_TIME) - System.currentTimeMillis()
                    if (planeDelayStartCatching <= 10L) planeDelayStartCatching = 1L
                    planeDelayStartCatching
                }
        LoadUsersScheduler.scheduleWithDelay(context, latencyForStartScheduler)
    }
}
