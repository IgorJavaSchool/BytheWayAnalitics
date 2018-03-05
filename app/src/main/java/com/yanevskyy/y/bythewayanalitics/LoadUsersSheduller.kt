package com.yanevskyy.y.bythewayanalitics

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log


const val DAY_TIME = 1000L //todo * 60L * 60L * 24L
const val PLANER_LOAD_ID = 1257

class LoadUsersScheduler : JobService() {
    companion object {
        const val NOTIFICATION_ID = 213 //"notificationStartCatching_1"
    }

    override fun onStartJob(params: JobParameters): Boolean {
        Log.d("tag1", "LoadUsersScheduler started")
        Log.d("tag1", "prepare notification 2")
        val notification = NotificationCompat.Builder(this, "notificationStartCatchingUsers")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("ByTheWay аналитика")
                .setContentText("включи интернет для запуска кеширования")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .addAction(R.drawable.ic_notification_start_catching, "начать загрузку",
                        PendingIntent.getService(this, 0,
                                Intent(this, CatcherUsersService::class.java), 0))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
        notification.flags = notification.flags or Notification.FLAG_ONGOING_EVENT or Notification.FLAG_NO_CLEAR

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(NOTIFICATION_ID, notification)
        return false
    }

    override fun onStopJob(params: JobParameters): Boolean {
        Log.d("tag", "LoadUsersScheduler stop id: " + params.jobId)
        return false //todo true
    }


}