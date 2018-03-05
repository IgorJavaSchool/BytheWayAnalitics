package com.yanevskyy.y.bythewayanalitics.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.firebase.mm.myapplication.TeamUserData
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.yanevskyy.y.bythewayanalitics.R
import com.yanevskyy.y.bythewayanalitics.TeamTravellers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_parse_emails.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Andrei_Gusenkov on 2/19/2018.
 */

class FragmentParseEmails : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_parse_emails,
                container, false)
        view?.findViewById<Button>(R.id.bt_getemails)?.setOnClickListener {
            sendRequest()
        }
        view?.findViewById<Button>(R.id.button2)?.setOnClickListener {
            var text = list.toString()
            writteText(list)
            saveDataAndSend(text)
        }
        return view
    }

    private fun saveDataAndSend(text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"

        intent.putExtra(Intent.EXTRA_SUBJECT, "Email from travel")
        intent.putExtra(Intent.EXTRA_TEXT, text)
        //   intent.setData(Uri.parse("mailto:default@recipient.com")); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        startActivity(intent)
    }

    var service: TeamTravellers? = null
    val list: CopyOnWriteArrayList<String> = CopyOnWriteArrayList()
    val url = ""
    private fun sendRequest() {
        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                        .setLenient()
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()

        service = retrofit.create(TeamTravellers::class.java)


        val countTest = AtomicInteger(0)

        runBlocking {
            List(21000) {
                //val job =
                async {
                    launch(UI) {
                        count_requests_text.text = it.toString()
                        Log.d("LOG thread name:", Thread.currentThread().name)
                    }
                    val user = getUserId(it)
                    Log.e("LOG USER $it", user.toString())
                    if (user.InUser.IsAgent == "0" && user.InUser.is_manager == "0" && user.InUser.is_robot == "0" && user.InUser.is_test == "0") {
                        list.add(user.InUser.email)
                        Log.d("LOG add email $it", user.InUser.email)
                    } else {
                        countTest.incrementAndGet()
                    }
                    launch(UI) {
                        count_test_users_text.text = countTest.get().toString()
                        count_emails_text.text = list.size.toString()
                    }
                }

                //job.join()
            }
            Log.d("LOG finnish 2:", Thread.currentThread().name + "s: ${list.size}")

        }
        Log.d("LOG finnish:", Thread.currentThread().name)
    }

    fun writteText(list: List<String>) {
        try {
            val filename = "email ${list.size}.txt"
            context?.openFileOutput(filename, Context.MODE_PRIVATE)?.use {
                list.forEach { list ->
                    it.write(list.toByteArray())
                    it.write(", ".toByteArray())
                }
            }
        } catch (e: Exception) {
            Log.e("LOGERROR", "EMAIL", e)
        }
    }

    suspend fun getUserId(id: Int): TeamUserData = service!!.getUser(id = id).await()
}
//        service!!.getUserRx(id = 8906)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : SingleObserver<TeamUserData?> {
//                    override fun onSuccess(user: TeamUserData) {
//                        Log.e("LOG", "user ${user.toString()}")
//                    }
//
//                    override fun onSubscribe(d: Disposable) {
//                    }
//
//                    override fun onError(e: Throwable) {
//                        Log.e("LOG", "ERROR", e)
//                    }
//                })