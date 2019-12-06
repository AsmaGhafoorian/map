package com.test.presentation.ui.address

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.test.presentation.R
import com.test.presentation.inflate
import com.test.presentation.model.AddressesModel
import kotlinx.android.synthetic.main.adapter_address.view.*

class AddressAdapter (val addressList: List<AddressesModel>,
                      val parentActivity: Activity
                      ) : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    private var mSelectedItem = 0
    override fun getItemCount(): Int = addressList.count()

    override fun onBindViewHolder(holder: AddressAdapter.ViewHolder, position: Int) {

        holder.bind(addressList[position])
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressAdapter.ViewHolder = ViewHolder(parent)


    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.adapter_address)) {

        @SuppressLint("SetTextI18n")
        fun bind(address: AddressesModel) {


            itemView.address.text = address.address
            itemView.name.text = address.first_name + " " + address.last_name
            itemView.mobile.text = address.coordinate_mobile
        }

    }
}
