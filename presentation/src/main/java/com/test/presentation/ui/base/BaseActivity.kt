package com.test.presentation.ui.base

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.test.presentation.R
import com.test.presentation.getAppInjector
import com.test.presentation.utils.LocaleManager
import com.test.presentation.utils.SharedPreference
import javax.inject.Inject


/**
 * Created by asma.
 */
open class BaseActivity :AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var sharedPreference: SharedPreference
    var loadingView: PageLoadingView? = null

    var customToolbar: View? = null

    val LANGUAGE_ENGLISH = "en"
    val LANGUAGE_PERSIAN = "fa"

    var language: String? = null
    var token = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppInjector().inject(this)
//        getColors()
//        language = LocaleManager.getLanguage(baseContext)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))

    }



    fun registerToolbar(){
        val layout = ActionBar.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val actionBar = supportActionBar
        actionBar!!.setDisplayShowTitleEnabled(false)
        actionBar.elevation = 0F

        val inflater = LayoutInflater.from(this)
        customToolbar = inflater.inflate(R.layout.toolbar, null)

        actionBar!!.setCustomView(customToolbar, layout)
        actionBar.setDisplayShowCustomEnabled(true)
        val parent = actionBar.customView.parent as Toolbar
        parent.setContentInsetsAbsolute(0, 0)
    }

    fun showPagesLoading() {
        loadingView = PageLoadingView(this)
        loadingView!!.setCancelable(false)
        loadingView!!.show()
    }

    fun hidePagesLoading() {
        if (loadingView != null && loadingView?.isShowing()!!)
            loadingView?.dismiss()
    }

}