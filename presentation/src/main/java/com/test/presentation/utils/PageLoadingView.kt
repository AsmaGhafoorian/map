package com.test.presentation.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatDialog
import android.view.ViewGroup
import com.test.presentation.R

class PageLoadingView(context: Context?) : AppCompatDialog(context) {

    var mContext : Context? = null
    init {
        mContext = context
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_loading)
        init(mContext!!)
    }


    private fun init(context: Context) {

        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}