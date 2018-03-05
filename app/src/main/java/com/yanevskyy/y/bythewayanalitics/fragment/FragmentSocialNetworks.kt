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
import kotlinx.android.synthetic.main.fragment_social_networks.*

class FragmentSocialNetworks : Fragment() {
    private var presenter: AppPresenter = App.INSTANCE.appPresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_social_networks, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var countAnyNetworks = 0
        var countTG = 0
        var countVK = 0
        var countWhatsapp = 0
        presenter.userDao.users.filter {user ->
            user.socialNetwork.isNotEmpty() }
                .forEach { user ->
                    countAnyNetworks++
                    user.socialNetwork["TG"]?.let { countTG++ }
                    user.socialNetwork["VK"]?.let { countVK++ }
                    user.socialNetwork["WHATSAPP"]?.let { countWhatsapp++ }
                }

        val percentAccountsWithTG = Math.round(countTG.toDouble() / countAnyNetworks * 100).toInt()
        val percentAccountsWithVK = Math.round(countVK.toDouble() / countAnyNetworks * 100).toInt()
        val percentAccountsWithWhatsapp = Math.round(countWhatsapp.toDouble() / countAnyNetworks * 100).toInt()

        displayValues(countAnyNetworks, percentAccountsWithTG, percentAccountsWithVK, percentAccountsWithWhatsapp,
                countTG, countVK, countWhatsapp)
    }

    private fun displayValues(countAnyNetworks: Int, percentTG: Int, percentVK: Int, percentWhatsapp: Int, countTG: Int,
                              countVK: Int, countWhatsapp: Int) {
        countAnyNetworkText.text = StringBuilder(context?.getString(R.string.contains_any_networks)).append(" ")
                .append(countAnyNetworks)
        countTGText.text = StringBuilder(context?.getString(R.string.contains_telegram)).append(" ")
                .append(countTG).append(" (").append(percentTG).append("%").append(")")
        countVKText.text = StringBuilder(context?.getString(R.string.contains_vk)).append(" ").append(countVK)
                .append(" (").append(percentVK).append("%").append(")")
        countWhatsappText.text = StringBuilder(context?.getString(R.string.contains_whatsapp)).append(" ")
                .append(countWhatsapp).append(" (").append(percentWhatsapp).append("%").append(")")
    }
}