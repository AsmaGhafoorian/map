package com.test.presentation.utils

import android.content.Context
import javax.inject.Inject

/**
 * Created by asma.
 */
class SharedPreference @Inject constructor(){


    val TOKEN_ID = "token_id"
    val TOKEN = "token"

     fun storeToken(context: Context, token: String) {

        var preference = context.getSharedPreferences(TOKEN_ID, 0)
        val editor = preference.edit()
        editor.putString(TOKEN, token)
        editor.commit()
    }

     fun retrievedToken(context: Context): String {

        val prefs = context.getSharedPreferences(TOKEN_ID, 0)
        var token =  prefs.getString(TOKEN, "")
        return token
    }
}