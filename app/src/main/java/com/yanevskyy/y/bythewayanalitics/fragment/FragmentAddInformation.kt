package com.yanevskyy.y.bythewayanalitics.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yanevskyy.y.bythewayanalitics.App
import com.yanevskyy.y.bythewayanalitics.AppPresenter
import com.yanevskyy.y.bythewayanalitics.R
import kotlinx.android.synthetic.main.fragment_add_info.*

class FragmentAddInformation : Fragment() {
    private var presenter: AppPresenter = App.INSTANCE.appPresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_add_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var countAddInformation = 0
        presenter.userDao.users
                .filter { user -> user.addInformation.isNotEmpty() && !user.addInformation.equals("null", true) }
                .forEach { countAddInformation++ }

        displayValues(countAddInformation)
    }

    private fun displayValues(countAddInformation: Int) {
        countAddInformationText.text = StringBuilder(countAddInformationText.text).append(countAddInformation.toString())
    }
}