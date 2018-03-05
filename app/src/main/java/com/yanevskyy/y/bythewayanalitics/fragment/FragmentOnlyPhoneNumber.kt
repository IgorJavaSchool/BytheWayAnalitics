package com.yanevskyy.y.bythewayanalitics.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yanevskyy.y.bythewayanalitics.App
import com.yanevskyy.y.bythewayanalitics.AppPresenter
import com.yanevskyy.y.bythewayanalitics.R
import kotlinx.android.synthetic.main.fragment_only_phone_number.*

class FragmentOnlyPhoneNumber : Fragment() {
    private var presenter: AppPresenter = App.INSTANCE.appPresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_only_phone_number, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var countOnlyPhoneNumber = 0
        presenter.userDao.users
                .filter { user ->
                    (user.email.isEmpty() || user.email.equals("null", true))
                            && user.phone.isNotEmpty() && !user.phone.equals("null", true)
                }
                .forEach { countOnlyPhoneNumber++ }

        displayValues(countOnlyPhoneNumber)
    }

    private fun displayValues(countOnlyPhoneNumber: Int) {
        countOnlyPhoneNumberText.text = StringBuilder(countOnlyPhoneNumberText.text).append(countOnlyPhoneNumber.toString())
    }
}