package com.test.presentation.injection.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.test.presentation.ViewModelFactory
import com.test.presentation.ViewModelKey
import com.test.presentation.ui.address.AddreddViewModel
import com.test.presentation.ui.register.RegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    internal abstract fun bindRegisterViewModel(factory: RegisterViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(AddreddViewModel::class)
    internal abstract fun bindAddressViewModel(factory: AddreddViewModel): ViewModel


}
