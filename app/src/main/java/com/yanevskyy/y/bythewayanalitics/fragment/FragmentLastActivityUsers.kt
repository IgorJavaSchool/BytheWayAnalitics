package com.yanevskyy.y.bythewayanalitics.fragment


import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.yanevskyy.y.bythewayanalitics.App
import com.yanevskyy.y.bythewayanalitics.AppPresenter
import com.yanevskyy.y.bythewayanalitics.R
import kotlinx.android.synthetic.main.fragment_last_activity_users.*
import java.util.*
import kotlin.collections.ArrayList


class FragmentLastActivityUsers : Fragment() {
    private var presenter: AppPresenter = App.INSTANCE.appPresenter
    private var timeLastActivityUser: Long = Calendar.getInstance().timeInMillis
    private lateinit var mDateListener: DatePickerDialog.OnDateSetListener
    private lateinit var calendar: Calendar
    private var items: MutableList<String> = ArrayList()
    private var emails: MutableList<String> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_last_activity_users, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        updateActiveUsers()

        mDateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            textDateActive.text = StringBuilder(dayOfMonth.toString()).append("/").append(month + 1).append("/").append(year)

            val calendar1 = Calendar.getInstance()
            calendar1.set(year, month, dayOfMonth)
            timeLastActivityUser = calendar1.time.time
            updateActiveUsers()
            userList.adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, items)
            userList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                items.remove(items[position])
                userList.adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, items)
            }
        }

        textDateActive.setOnClickListener {
            calendar = Calendar.getInstance()
            val dialog = DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateListener,
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }

        startSendOnSelectedEmails.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, emails.toTypedArray())
            intent.putExtra(Intent.EXTRA_TEXT, "Body of email")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // this will make such that when user returns to your app, your app is displayed, instead of the email app.
            startActivity(intent)
        }
    }

    fun updateActiveUsers() {
        installActiveUsers()
        text_count_users.text = StringBuilder("колличество пользователей: ").append((emails.size).toString())
    }

    fun installActiveUsers() {
        emails.clear()
        items.clear()
        for (user in presenter.userDao.users) {
            val timestamp = user.data
            if (timestamp <= timeLastActivityUser) {
                val name = user.name
                val lastName = user.lastName
                val email = user.email
                if (email.isNotEmpty() && !email.equals("null", true)) {
                    emails.add(user.email)
                    items.add("$name $lastName : $email")
                }
            }
        }
    }
}