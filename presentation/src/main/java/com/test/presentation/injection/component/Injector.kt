package com.test.presentation.injection.component

import com.test.presentation.injection.module.AppModule
import com.test.presentation.injection.module.NetworkModule
import com.test.presentation.injection.module.RepositoryModule
import com.test.presentation.injection.module.ViewModelModule
import com.test.presentation.ui.address.AddressActivity
import com.test.presentation.ui.base.BaseActivity
import com.test.presentation.ui.map.MapActivity
import com.test.presentation.ui.register.RegisterActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (NetworkModule::class), (RepositoryModule::class), (ViewModelModule::class)])
interface Injector {

    fun inject(activity: BaseActivity)
    fun inject(activity: RegisterActivity)
    fun inject(activity: MapActivity)
    fun inject(activity: AddressActivity)

}
