package com.test.presentation.navigation

import android.app.Activity
import android.content.Intent
import com.test.presentation.model.RegisterBodyModel
import com.test.presentation.ui.address.AddressActivity
import com.test.presentation.ui.map.MapActivity
import com.test.presentation.utils.REGISTER_DATA
import javax.inject.Inject



class registerNavigator @Inject constructor() {
    fun navigateToMapActivity(activity: Activity, registerData: RegisterBodyModel?) {
        val intent = Intent(activity, MapActivity::class.java)
        intent.putExtra(REGISTER_DATA, registerData)
        activity.startActivity(intent)
    }
}

class mapNavigator @Inject constructor() {
    fun navigateToAddressActivity(activity: Activity) {
        val intent = Intent(activity, AddressActivity::class.java)
        activity.startActivity(intent)
    }
}


