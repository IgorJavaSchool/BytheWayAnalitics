package com.yanevskyy.y.bythewayanalitics

import android.app.Application
import android.preference.PreferenceManager
import com.yanevskyy.y.bythewayanalitics.repository.UsersRepository

class App : Application() {
    companion object {
        lateinit var INSTANCE: App
    }

    val appPresenter: AppPresenter = AppPresenter()
    val dbManager = DbManager(this)
    val userRepository = UsersRepository()

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        if (PreferenceManager.getDefaultSharedPreferences(this).getLong("END_START_LOADING_USERS", 0L) == 0L)
            LoadUsersScheduler.scheduleWithDelay(this, DAY_TIME)
    }
}