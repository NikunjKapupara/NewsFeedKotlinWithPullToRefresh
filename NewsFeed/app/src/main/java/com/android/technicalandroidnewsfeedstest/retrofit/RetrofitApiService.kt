package com.android.technicalandroidnewsfeedstest.retrofit


import com.android.technicalandroidnewsfeedstest.ws_models.NewsFeedModel
import io.reactivex.Observable
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.GET


/**
 * Created by N!K$ on 8/24/18.
 */
interface RetrofitApiService {

    /**
     * CREATE INSTANCE FOR API SERVICE
     */
    companion object {

        fun create(): RetrofitApiService {

            val httpClient = OkHttpClient.Builder()

            /// to print the App Log I added -> HttpLoggingInterceptor()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(interceptor)

            /// to add the web service request header
            httpClient.addInterceptor { chain ->

                val original = chain.request()
                val builder = original.newBuilder()
                builder.addHeader("Accept", "application/json")
                builder.addHeader("Content-Type", "application/json; charset=utf-8").build()


                val request = builder.build()
                 chain.proceed(request)
            }


            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(HttpConstants.BASE_URL)
                    .client(httpClient.build())
                    .build()

            return retrofit.create(RetrofitApiService::class.java)
        }
    }



    /////////////////////////////////////////////////////////////////////////////////////////////
                                        /**
                                         * WEB SERVICES
                                         */
    /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * GET ALL News Feeds
     * sample URL: https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json
      */
    @GET("s/2iodh4vg0eortkl/facts.json")
    fun getNewsFeed(): Observable<NewsFeedModel>

}