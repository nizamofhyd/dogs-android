package com.dogs.application

import android.app.Application
import com.dogs.di.DependencyInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class DogApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DependencyInjector.init(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return injector
    }
}