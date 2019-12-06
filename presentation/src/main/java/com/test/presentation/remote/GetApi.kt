package com.test.presentation.remote

import com.test.presentation.model.AddressesModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * Created by asma.
 */

interface GetAddressesApi{
    @GET("api/karfarmas/address")
    fun getAddresses() : Single<List<AddressesModel>>
}

