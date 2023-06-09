package com.dnovaes.csgolive.common.di

import com.dnovaes.csgolive.common.data.models.Dispatcher
import com.dnovaes.csgolive.common.data.models.DispatcherInterface
import com.dnovaes.csgolive.common.data.remote.PANDASCORE_SERVICE_URL
import com.dnovaes.csgolive.common.data.remote.PandaScoreAPIInterface
import com.dnovaes.csgolive.common.data.remote.interceptor.AuthInterceptor
import com.dnovaes.csgolive.common.data.remote.interceptor.AuthInterceptorInterface
import com.dnovaes.csgolive.common.utilities.serializers.LocalDateTimeSerializer
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        const val PANDA_RETROFIT = "pandaRetrofit"
        const val PANDA_HTTP_CLIENT = "pandaOkhttpClient"
    }

    @Provides
    fun providesDispatcher(): DispatcherInterface = Dispatcher()

    @Provides
    fun providesAuthInterceptor(): AuthInterceptorInterface = AuthInterceptor()

    @Named(PANDA_HTTP_CLIENT)
    @Provides
    fun providesClient(
        authInterceptor: AuthInterceptorInterface,
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            //.addInterceptor(LoggerInterceptor())
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    fun providesJsonKotlinSerializationConfig(): Json {
        return Json {
            serializersModule = SerializersModule {
                contextual(LocalDateTime::class, LocalDateTimeSerializer)
            }
            ignoreUnknownKeys = true
        }
    }

    @Named(PANDA_RETROFIT)
    @Provides
    fun providesRetrofit(
        @Named(PANDA_HTTP_CLIENT) okHttpClient: OkHttpClient,
        json: Json
    ) : Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(PANDASCORE_SERVICE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun providesTcgService(
        @Named(PANDA_RETROFIT) retrofit: Retrofit
    ) : PandaScoreAPIInterface {
        return retrofit.create(PandaScoreAPIInterface::class.java)
    }
}
