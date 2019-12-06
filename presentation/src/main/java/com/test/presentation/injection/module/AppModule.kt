package com.test.presentation.injection.module

import com.test.presentation.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: App) {

    @Provides
    @Singleton
    fun provideApp(): App = app
}
