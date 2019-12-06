package com.test.presentation.injection.module

import com.test.presentation.remote.GetAddressesRepository
import com.test.presentation.remote.GetAddressesRepositoryImp
import com.test.presentation.remote.PostRegisterDataRepository
import com.test.presentation.remote.PostRegisterDataRepositoryImp
import dagger.Binds
import dagger.Module

   @Module
   abstract class RepositoryModule {

      @Binds
      abstract fun bindRegisterRepository(repository: PostRegisterDataRepositoryImp): PostRegisterDataRepository

      @Binds
      abstract fun bindAddressRepository(repository: GetAddressesRepositoryImp): GetAddressesRepository

   }
