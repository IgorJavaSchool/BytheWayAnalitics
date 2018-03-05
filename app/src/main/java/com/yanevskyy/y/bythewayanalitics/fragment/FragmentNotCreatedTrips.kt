package com.yanevskyy.y.bythewayanalitics.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yanevskyy.y.bythewayanalitics.App
import com.yanevskyy.y.bythewayanalitics.AppPresenter
import com.yanevskyy.y.bythewayanalitics.R
import kotlinx.android.synthetic.main.fragment_not_created_trips.*

class FragmentNotCreatedTrips : Fragment() {
    private var presenter: AppPresenter = App.INSTANCE.appPresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_not_created_trips, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var countNotCreatedTrips = 0
        presenter.userDao.users.filter { user -> user.cities.isEmpty() }.forEach { countNotCreatedTrips++ }
        val percentsNotCreatedTrips = Math.round(countNotCreatedTrips.toDouble() / presenter.userDao.users.size * 100).toInt()

        displayValues(countNotCreatedTrips, percentsNotCreatedTrips)
    }

    private fun displayValues(countNotCreatedTrips: Int, percentsNotCreatedTrips: Int) {
        countNotCreatedTripsText.text = StringBuilder(context?.getString(R.string.not_created_trips)).append(" ")
                .append(countNotCreatedTrips).append(" (").append(percentsNotCreatedTrips).append("%").append(")")
    }
}