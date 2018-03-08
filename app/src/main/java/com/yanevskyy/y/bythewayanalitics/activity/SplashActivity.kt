package com.yanevskyy.y.bythewayanalitics.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.firebase.mm.myapplication.User
import com.yanevskyy.y.bythewayanalitics.App
import com.yanevskyy.y.bythewayanalitics.AppPresenter
import com.yanevskyy.y.bythewayanalitics.R
import com.yanevskyy.y.bythewayanalitics.UserDao
import com.yanevskyy.y.bythewayanalitics.repository.OnRequestedUsers

class SplashActivity : AppCompatActivity() {
    var presenter: AppPresenter = App.INSTANCE.appPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        App.INSTANCE.userRepository.requestAllUsers(object : OnRequestedUsers() {
            override fun onSuccessRequested(users: MutableList<User>) {
                presenter.userDao = UserDao(users)
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
        })
    }
}