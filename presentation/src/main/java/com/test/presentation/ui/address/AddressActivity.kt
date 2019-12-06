package com.test.presentation.ui.address

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.test.presentation.*
import com.test.presentation.model.AddressesModel
import com.test.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.toolbar.*

class AddressActivity : BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        getAppInjector().inject(this)

        registerToolbar()
        addressTitle.visible()
        backBtn.visible()
        backBtn.setOnClickListener {
            finish()
        }
        getAddresses()
    }


    private fun getAddresses(){
        withViewModel<AddreddViewModel>(viewModelFactory){
             getAddresses(true, this@AddressActivity)
            observe(address, ::getAddressesResponse)
        }
    }

    private fun getAddressesResponse(data: Data<List<AddressesModel>>?) {
        data?.let {

            when (it.dataState) {

                DataState.LOADING -> {
                    Log.d("=========>", "Loading")
                    showPagesLoading()
                }
                DataState.SUCCESS -> {
                    Log.d("=========>", "Success")
                    it.data?.let {

                        hidePagesLoading()
                        addressRecyclerView.adapter = AddressAdapter(it, this)
                        addressRecyclerView.adapter?.notifyDataSetChanged()
                    }!!
                }
                DataState.ERROR -> {
                    hidePagesLoading()
                    Toast.makeText(this, data.message, Toast.LENGTH_LONG).show()
                    Log.d("=========>", "Error " + data.message.toString())

                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}