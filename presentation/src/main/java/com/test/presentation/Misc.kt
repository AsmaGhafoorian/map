package com.test.presentation

import android.app.Activity
import android.arch.lifecycle.*
import android.support.v4.app.FragmentActivity
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.test.presentation.injection.component.Injector

typealias f<T> = (T) -> Unit

val Activity.app: App get() = application as App

fun AppCompatActivity.getAppInjector(): Injector = (app).injector

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View = LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)

fun ImageView.loadCircleAvatar(imagePath: String) = Glide.with(this).load(imagePath).apply(RequestOptions.circleCropTransform()).into(this)
fun ImageView.loadAvatar(imagePath: String) = Glide.with(this).load(imagePath).into(this)


var options = RequestOptions()
        .centerCrop()
        .placeholder(R.mipmap.ic_launcher)
        .fallback(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher)



fun ImageView.loadProfileAvatar(url: String) = Glide.with(this).load(url).apply(options).into(this)

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun SwipeRefreshLayout.startRefreshing() {
    isRefreshing = true
}

fun SwipeRefreshLayout.stopRefreshing() {
    isRefreshing = false
}

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(viewModelFactory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, viewModelFactory)[T::class.java]
}

inline fun <reified T : ViewModel> FragmentActivity.withViewModel(viewModelFactory: ViewModelProvider.Factory, body: T.() -> Unit): T {
    val vm = getViewModel<T>(viewModelFactory)
    vm.body()
    return vm
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}
