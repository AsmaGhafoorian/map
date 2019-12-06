package com.test.presentation.injection.module

import com.test.presentation.remote.GetAddressesApi
import com.test.presentation.remote.RegisterApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    private val BASE_URL = "http://stage.achareh.ir/"
    private val CONNECT_TIMEOUT = 60L
    private val READ_TIMEOUT = 10L
    private val WRITE_TIMEOUT = 10L
    private val username = "09822222222"
    private val password = "sana1234"

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        val clientBuilder = OkHttpClient.Builder()
//        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(BasicAuthInterceptor(username, password))
            clientBuilder.addInterceptor(httpLoggingInterceptor)
//        }
        clientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    fun PostRegisterApi(retrofit: Retrofit): RegisterApi = retrofit.create(RegisterApi::class.java)

    @Provides
    fun GetaddressApi(retrofit: Retrofit): GetAddressesApi = retrofit.create(GetAddressesApi::class.java)

}
