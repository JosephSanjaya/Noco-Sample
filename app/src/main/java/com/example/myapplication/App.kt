package com.example.myapplication

import android.app.Application
import android.content.SharedPreferences
import dagger.hilt.android.HiltAndroidApp
import dev.b3nedikt.restring.Restring
import java.util.Locale
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {

    @Inject
    lateinit var preferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        Restring.init(this)
//        runCatching {
//            val locale = Locale.forLanguageTag(preferences.getString("locale", "").toString())
//            Restring.locale = locale
//        }.onFailure {
//            Restring.locale = Locale("id", "ID")
//        }
        Restring.locale = Locale.getDefault()
    }
}