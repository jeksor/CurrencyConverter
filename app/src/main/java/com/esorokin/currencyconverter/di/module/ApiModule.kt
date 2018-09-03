package com.esorokin.currencyconverter.di.module

import com.esorokin.currencyconverter.R
import com.esorokin.currencyconverter.RevolutApi
import com.esorokin.currencyconverter.app.StringProvider
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class ApiModule {
    companion object {
        private const val BASE_API_QUALIFIER = "BASE_API_QUALIFIER"
    }

    @Provides
    @Named(BASE_API_QUALIFIER)
    @Singleton
    fun provideBaseApiUrl(stringProvider: StringProvider): String = stringProvider.getString(R.string.base_api_url)

    @Provides
    @Named(BASE_API_QUALIFIER)
    @Singleton
    fun provideBaseRetrofit(
        @Named(BASE_API_QUALIFIER) baseApiUrl: String,
        retrofitBuilder: Retrofit.Builder
    ): Retrofit = retrofitBuilder
        .baseUrl(baseApiUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()

    @Provides
    @Singleton
    fun provideRevolutApi(@Named(BASE_API_QUALIFIER) retrofit: Retrofit): RevolutApi = retrofit.create(RevolutApi::class.java)
}
