package com.yanevskyy.y.bythewayanalitics

import android.app.Application
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Handler
import android.util.Log

class App : Application() {
    companion object {
        lateinit var INSTANCE: App
    }

    var appPresenter: AppPresenter = AppPresenter()

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        startLoadUsersService(this)

        val handler = Handler()
        handler.postDelayed({
            val jobScheduler = App@ this.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            for (currentPendingJob in jobScheduler.allPendingJobs) {
                Log.d("tag", "scheduler: " + currentPendingJob.id)
            }
            if (!jobScheduler.allPendingJobs.any { it.id == PLANER_LOAD_ID })
                Log.d("tag", "startLoadUsersService scheduler NOT started")
            else Log.d("tag", "handler startLoadUsersService scheduler already started")
        }, 4000L)
    }

    private fun startLoadUsersService(context: Context) {
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        if (!jobScheduler.allPendingJobs.any { it.id == PLANER_LOAD_ID }) {
            jobScheduler.schedule(JobInfo.Builder(PLANER_LOAD_ID, ComponentName(context, LoadUsersScheduler::class.java))
//                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setMinimumLatency(DAY_TIME)
                    .build())
            Log.d("tag", "startLoadUsersService scheduler started")
        } else Log.d("tag", "startLoadUsersService scheduler already started")
    }
}