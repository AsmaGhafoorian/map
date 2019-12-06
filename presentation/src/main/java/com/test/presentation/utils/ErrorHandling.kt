package io.noxel.presentation.ui.utils

import android.app.Activity
import com.test.presentation.R
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Created by asma.
 */
open class ErrorHandling {
    fun manageError(throwable: Throwable, activity: Activity) : String?{
        var detail : String? = ""
        if((throwable is HttpException)) {
            when((throwable as HttpException).code()){
                in 500..599->  detail = activity.getString(R.string.server_error)

                in 400..499 ->   detail = activity.getString(R.string.server_disconnect)

            }

        }else{
            if(throwable is IOException){
                if(throwable is SocketTimeoutException){
                    detail = activity.getString(R.string.time_out)
                }else
                    detail = activity.getString(R.string.no_connection)
            }else{
                if(throwable is NoSuchElementException){
                    detail = activity.getString(R.string.server_error)
                }
            }
        }
        return detail
    }
}