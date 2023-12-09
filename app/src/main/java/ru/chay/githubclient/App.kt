package ru.chay.githubclient

import android.app.Application
import ru.chay.githubclient.di.AppModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appModule = AppModule.Base(this)
    }

    companion object {
        lateinit var appModule: AppModule
    }
}