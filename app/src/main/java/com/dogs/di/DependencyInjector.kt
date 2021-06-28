package com.dogs.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.dogs.BuildConfig
import com.dogs.application.DogApplication
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber

class DependencyInjector {

    companion object {

        fun init(application: DogApplication) {

            DaggerDogComponent
                .builder()
                .application(application)
                .build()
                .inject(application)

            if (BuildConfig.DEBUG) {
                Timber.plant(Timber.DebugTree())
            }

            application.registerActivityLifecycleCallbacks(object :
                Application.ActivityLifecycleCallbacks {

                override fun onActivityPaused(p0: Activity) {

                }

                override fun onActivityResumed(p0: Activity) {

                }

                override fun onActivityStarted(p0: Activity) {

                }

                override fun onActivityDestroyed(p0: Activity) {

                }

                override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

                }

                override fun onActivityStopped(p0: Activity) {

                }

                override fun onActivityCreated(p0: Activity, savedInstanceState: Bundle?) {
                    injectActivityIfRequired(p0)
                }
            })

        }

        private fun injectActivityIfRequired(activity: Activity?) {
            if (activity is Injectable) {
                AndroidInjection.inject(activity)
            }
            if (activity is FragmentActivity) {
                activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(
                        object : FragmentManager.FragmentLifecycleCallbacks() {
                            override fun onFragmentCreated(
                                fm: FragmentManager,
                                f: Fragment,
                                savedInstanceState: Bundle?
                            ) {
                                if (f is Injectable) {
                                    AndroidSupportInjection.inject(f)
                                }
                            }
                        }, true
                    )
            }
        }

    }
}