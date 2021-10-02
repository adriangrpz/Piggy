package com.piggy.piggy

import android.app.Application
import io.realm.Realm

class PiggyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
    }
}