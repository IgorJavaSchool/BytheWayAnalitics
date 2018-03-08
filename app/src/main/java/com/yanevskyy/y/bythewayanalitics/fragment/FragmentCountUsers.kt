package com.yanevskyy.y.bythewayanalitics.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yanevskyy.y.bythewayanalitics.App
import com.yanevskyy.y.bythewayanalitics.AppPresenter
import com.yanevskyy.y.bythewayanalitics.OnInstallDates
import com.yanevskyy.y.bythewayanalitics.R
import kotlinx.android.synthetic.main.fragment_all_users.*
import java.util.concurrent.TimeUnit


class FragmentCountUsers : Fragment() {
    private var presenter: AppPresenter = App.INSTANCE.appPresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_all_users, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var countAllUsers = 0
        presenter.userDao.users.forEach { countAllUsers++ }

        App.INSTANCE.dbManager.installDatesInUsers(App.INSTANCE.appPresenter.userDao.users.toMutableList(), object : OnInstallDates {
            override fun onInstalled() {
                val timeOneDayAgo = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1)
                val countUsersExistOneDay = presenter.userDao.users.count { it.catchingDate > timeOneDayAgo || it.catchingDate == 0L }
                displayValues(countAllUsers, countUsersExistOneDay)
            }
        })
    }

    private fun displayValues(countAllUsers: Int, countUsersExistOneDay: Int) {
        countAllUsersText.text = StringBuilder(countAllUsersText.text).append(countAllUsers.toString())
        countNewUsersText.text = StringBuilder(countNewUsersText.text).append(countUsersExistOneDay.toString())
    }
}