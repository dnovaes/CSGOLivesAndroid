package com.dnovaes.csgolive.common.data.remote.interceptor

import com.dnovaes.csgolive.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

interface AuthInterceptorInterface: Interceptor

class AuthInterceptor: AuthInterceptorInterface {

    companion object {
        const val AUTHORIZATION = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(AUTHORIZATION, BuildConfig.PANDASCORE_API_KEY)
            .build()
        println("LOG request sent: $request")
        return chain.proceed(request)
    }
}