package com.test.presentation.ui.address

import android.app.Activity
import android.arch.lifecycle.ViewModel
import com.test.presentation.Data
import com.test.presentation.DataState
import com.test.presentation.model.AddressesModel
import com.test.presentation.model.RegisterBodyModel
import com.test.presentation.remote.GetAddressesRepository
import com.test.presentation.remote.PostRegisterDataRepository
import io.noxel.presentation.ui.utils.ErrorHandling
import io.noxel.presentation.ui.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddreddViewModel @Inject constructor(private val repository: GetAddressesRepository): ViewModel(){

    val address = SingleLiveEvent<Data<List<AddressesModel>>>()
    private var compositeDisposable = CompositeDisposable()

    fun getAddresses(refresh: Boolean, activity: Activity) =
            compositeDisposable.add(repository.GetAddresses(refresh)
                    .doOnSubscribe { address.postValue(Data(dataState = DataState.LOADING, data = address.value?.data, message = null)) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({
                        address.postValue(Data(dataState = DataState.SUCCESS, data = it, message = null))
                    }, {
                        var error =  ErrorHandling()
                        var detail = error.manageError(it, activity)
                        address.postValue(Data(dataState = DataState.ERROR, data = address.value?.data, message = detail)) }))


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}