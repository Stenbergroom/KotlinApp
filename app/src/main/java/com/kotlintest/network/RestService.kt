package com.kotlintest.network

import com.kotlintest.KotlinApp
import com.kotlintest.R
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.RxJavaCallAdapterFactory
import retrofit2.ScalarsConverterFactory

/**
 * Created by alex on 12/28/16.
 */
class RestService {
    companion object {

        private val TAG = RestService::class.java.simpleName
        val JSON = MediaType.parse("application/json; charset=utf-8")

        @JvmStatic
        fun createRestService(): KotlinAPI {
            val httpClient: OkHttpClient
            httpClient = OkHttpClient.Builder()
                    //here we can add Interceptor for dynamical adding headers
                    .addNetworkInterceptor { chain ->
                        val request = chain
                                .request()
                                .newBuilder()
                                /*.addHeader("Authorization", "Token " + token)*/
                                /*.addHeader("Content-Type", "application/json")*/
                                .build()
                        chain.proceed(request)
                    }
                    //here we adding Interceptor for full level logging
                    //.addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(KotlinApp.kotlinApp!!.getAppContext().getString(R.string.main_endpoint))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(httpClient)
                    .build()

            return retrofit.create(KotlinAPI::class.java)
        }
    }
}
