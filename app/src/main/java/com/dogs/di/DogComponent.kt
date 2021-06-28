package com.dogs.di

import android.app.Application
import com.dogs.application.DogApplication
import com.dogs.data.di.DataModule
import com.dogs.domain.di.DomainModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBuilderModule::class, FragmentBuilderModule::class, DomainModule::class, DataModule::class])
interface DogComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): DogComponent
    }

    fun inject(application: DogApplication)
}