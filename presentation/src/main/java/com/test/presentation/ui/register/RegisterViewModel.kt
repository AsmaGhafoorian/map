package com.test.presentation.ui.register

import android.app.Activity
import android.arch.lifecycle.ViewModel
import com.test.presentation.Data
import com.test.presentation.DataState
import com.test.presentation.model.RegisterBodyModel
import com.test.presentation.remote.PostRegisterDataRepository
import io.noxel.presentation.ui.utils.ErrorHandling
import io.noxel.presentation.ui.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by asma.
 */
class RegisterViewModel @Inject constructor(private val repository: PostRegisterDataRepository): ViewModel(){

    val submit = SingleLiveEvent<Data<Any>>()
    private var compositeDisposable = CompositeDisposable()

    fun getConfirmationCode(registerData: RegisterBodyModel, activity: Activity) =
            compositeDisposable.add(repository.PostRegisterData(registerData)
                    .doOnSubscribe { submit.postValue(Data(dataState = DataState.LOADING, data = submit.value?.data, message = null)) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({
                        submit.postValue(Data(dataState = DataState.SUCCESS, data = it, message = null))
                    }, {
                        var error =  ErrorHandling()
                        var detail = error.manageError(it, activity)
                        submit.postValue(Data(dataState = DataState.ERROR, data = submit.value?.data, message = detail)) }))


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}