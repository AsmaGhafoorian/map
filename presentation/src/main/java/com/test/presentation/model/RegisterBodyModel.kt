package com.test.presentation.model

import java.io.Serializable

/**
 * Created by asma.
 */

data class RegisterBodyModel(
        var region : Int?,
        var address:String?,
        var lat: Double?,
        var lng : Double?,
        var coordinate_mobile :String?,
        var coordinate_phone_number :String?,
        var first_name : String?,
        var last_name : String?,
        var gender : String?
):Serializable