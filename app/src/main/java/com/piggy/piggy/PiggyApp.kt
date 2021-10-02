package com.piggy.piggy

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.log.LogLevel
import io.realm.log.RealmLog

lateinit var realmThread: Realm

// global Kotlin extension that resolves to the short version
// of the name of the current class. Used for labelling logs.
inline fun <reified T> T.TAG(): String = T::class.java.simpleName

class PiggyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val realmName = "Piggy"
        val config = RealmConfiguration.Builder().name(realmName).build()
        realmThread = Realm.getInstance(config)

        // Enable more logging in debug mode
        if (BuildConfig.DEBUG) {
            RealmLog.setLevel(LogLevel.ALL)
        }
    }
}