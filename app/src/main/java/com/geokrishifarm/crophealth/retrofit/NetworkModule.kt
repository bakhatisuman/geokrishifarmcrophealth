package com.geokrishifarm.crophealth.retrofit

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


class NetworkModule {

    companion object {
        var instancee: NetworkModule? = null


        var API_BASE_URL = "https://geokrishi.farm/api/"

        const val HTTP_DIR_CACHE = "mapper_cache"
        const val CACHE_SIZE = 200 * 1024 * 1024


        fun getInstance(): NetworkModule {
            if (instancee == null) {
                instancee = NetworkModule()
            }
            return instancee!!
        }
    }


    /*private fun provideCache(context: Context): Cache {
        return Cache(File(context.cacheDir, HTTP_DIR_CACHE), CACHE_SIZE.toLong())
    }*/

    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    private fun provideOkHttpClient(
        context: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()

            val header : String? = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzg4NDA4MTIzLCJqdGkiOiI0YTFmZGNlZmQ4ZjU0ZmM1OWNmZGI3YmJjNjhmOTRkYyIsInVzZXJfaWQiOjk1ODAzfQ.9kX4fWOXUwKFc8Krprz0zB_YRtmDedjwJl5f8l5D3aw"
//            val sessionManager = SessionManager.getInstance(context)
//            val session = sessionManager.findOne()
//            if (session != null) {
//                header = session.user!!.access
//            }

            var request: Request? = null
            request = if (header.isNullOrEmpty()) {
                original.newBuilder()
                    .method(original.method, original.body)
                    .build()
            } else {
                original.newBuilder()
                    .header("Authorization", "Bearer $header")
                    .method(original.method, original.body)
                    .build()
            }

            chain.proceed(request)
        }.addInterceptor(httpLoggingInterceptor)
            .readTimeout(3, TimeUnit.MINUTES)
            .connectTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
//            .cache(cache)
            .build()
    }


    private fun provideGson(): Gson {
        return GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .create()
    }

    private fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    private fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(gsonConverterFactory)
        return builder.client(okHttpClient).build()
    }

    fun provideApiService(context: Context): APIService {
        val gsonConverterFactory = provideGsonConverterFactory(provideGson())
        val okHttpClient =
            provideOkHttpClient(context, provideHttpLoggingInterceptor())
        val retrofit = provideRetrofit(okHttpClient, gsonConverterFactory)
        Log.d("NetworkModule: ","instanceRS")
        return retrofit.create(APIService::class.java)
    }


}
