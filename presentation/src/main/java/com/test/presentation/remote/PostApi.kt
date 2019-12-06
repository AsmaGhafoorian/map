package com.test.presentation.remote

import com.test.presentation.model.RegisterBodyModel
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by asma.
 */


interface RegisterApi {

    @Headers("content-type: application/json")

    @POST("api/karfarmas/address")
    fun postData( @Body data: RegisterBodyModel) : Single<Any>
}
