package com.test.presentation.remote

import com.test.presentation.model.AddressesModel
import com.test.presentation.model.RegisterBodyModel
import io.reactivex.Single

/**
 * Created by asma.
 */


interface PostRegisterDataRepository {

    val Key: String
    fun PostRegisterData(data: RegisterBodyModel) : Single<Any>
}


interface GetAddressesRepository {

    val Key: String
    fun GetAddresses(refresh: Boolean) : Single<List<AddressesModel>>
}
