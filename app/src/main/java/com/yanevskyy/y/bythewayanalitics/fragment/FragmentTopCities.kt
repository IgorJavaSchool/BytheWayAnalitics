package com.yanevskyy.y.bythewayanalitics.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yanevskyy.y.bythewayanalitics.App
import com.yanevskyy.y.bythewayanalitics.AppPresenter
import com.yanevskyy.y.bythewayanalitics.R
import kotlinx.android.synthetic.main.fragment_top_cities.*

class FragmentTopCities : Fragment() {
    private var presenter: AppPresenter = App.INSTANCE.appPresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_top_cities, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val allFirstCities = HashMap<String, Int>()
        val allLastCities = HashMap<String, Int>()
        presenter.userDao.users
                .filter { user -> user.cities.isNotEmpty() }
                .forEach { user ->
                    user.cities["first_city"]?.let { userFirstCity ->
                        allFirstCities.put(userFirstCity, allFirstCities[userFirstCity]?.inc() ?: 1)
                    }
                    user.cities["last_city"]?.let { userLastCity ->
                        allLastCities.put(userLastCity, allLastCities[userLastCity]?.inc() ?: 1)
                    }
                }

        var countMaxFirstCity = Int.MIN_VALUE
        var topFirstCity = ""
        allFirstCities.forEach { pair ->
            if (pair.value > countMaxFirstCity) {
                topFirstCity = pair.key
                countMaxFirstCity = pair.value
            }
        }

        var countMaxLastCity = Int.MIN_VALUE
        var topLastCity = ""
        allLastCities.forEach { pair ->
            if (pair.value > countMaxLastCity) {
                topLastCity = pair.key
                countMaxLastCity = pair.value
            }
        }

        displayValues(topFirstCity, topLastCity)
    }

    private fun displayValues(topFirstCity: String, topLastCity: String) {
        topFirstCityText.text = StringBuilder(topFirstCityText.text).append(topFirstCity)
        topLastCityText.text = StringBuilder(topLastCityText.text).append(topLastCity)
    }
}