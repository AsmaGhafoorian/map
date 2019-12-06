package com.test.presentation.remote

import com.test.presentation.cache.Cache
import com.test.presentation.model.AddressesModel
import com.test.presentation.model.RegisterBodyModel
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by asma.
 */

@Singleton
class GetAddressesRepositoryImp @Inject constructor(
        private val api : GetAddressesApi,
        private val cache : Cache<List<AddressesModel>>
        ) : GetAddressesRepository {
    override val Key = "Address"

    override fun GetAddresses(refresh: Boolean): Single<List<AddressesModel>> = when(refresh) {
        true -> {api.getAddresses().flatMap {set(it)  }}
        false -> {cache.load(Key).onErrorResumeNext { GetAddresses(true) }}
    }
    private fun set(requests: List<AddressesModel>) = cache.save(Key, requests)
}



@Singleton
class PostRegisterDataRepositoryImp @Inject constructor(
        private val api : RegisterApi
) : PostRegisterDataRepository {
    override val Key = "userData"

    override fun PostRegisterData(data: RegisterBodyModel): Single<Any> {
        return api.postData(data).map { it }
    }
}
