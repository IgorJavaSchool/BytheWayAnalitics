package com.yanevskyy.y.bythewayanalitics

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat


const val DAY_TIME = 1000L * 60 * 60 * 24
const val PLANER_LOAD_ID_ON_BOOT = 1255

class LoadUsersScheduler : JobService() {
    companion object {
        const val NOTIFICATION_ID = 213

        fun scheduleWithDelay(context: Context, delay: Long = DAY_TIME) {
            (context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler)
                    .schedule(JobInfo.Builder(PLANER_LOAD_ID_ON_BOOT, ComponentName(context, LoadUsersScheduler::class.java))
                            .setMinimumLatency(delay)
                            .build())
        }
    }

    override fun onStartJob(params: JobParameters): Boolean {
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

    override fun onStopJob(params: JobParameters) = false
}