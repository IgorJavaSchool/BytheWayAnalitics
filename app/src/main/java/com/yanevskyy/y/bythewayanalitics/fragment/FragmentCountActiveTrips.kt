package com.yanevskyy.y.bythewayanalitics.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yanevskyy.y.bythewayanalitics.App
import com.yanevskyy.y.bythewayanalitics.AppPresenter
import com.yanevskyy.y.bythewayanalitics.R
import kotlinx.android.synthetic.main.fragment_count_active_trips.*
import java.util.*

class FragmentCountActiveTrips : Fragment() {
    private var presenter: AppPresenter = App.INSTANCE.appPresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_count_active_trips, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var countActiveTrips = 0
        val currentTime = Calendar.getInstance().timeInMillis - (1000 * 60 * 60 * 24)
        presenter.userDao.users
                .filter { user ->
                    user.dates["start_date"]?.let { it > currentTime }
                            ?: (user.dates["end_date"]?.let { it > currentTime } ?: false)
                }
                .forEach { countActiveTrips++ }

        displayValues(countActiveTrips)
    }

    private fun displayValues(countActiveTrips: Int) {
        countActiveTripsText.text = StringBuilder(countActiveTripsText.text).append(countActiveTrips.toString())
    }
}