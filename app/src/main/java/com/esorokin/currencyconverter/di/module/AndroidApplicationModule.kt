package com.esorokin.currencyconverter.di.module

import android.content.Context
import com.esorokin.currencyconverter.app.StringProvider
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
class AndroidApplicationModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideLocale(): Locale = Locale.getDefault()

    @Provides
    @Singleton
    fun provideStringProvider(): StringProvider = object : StringProvider {
        override fun getString(stringRes: Int, vararg formatArgs: Any): String = context.getString(stringRes, formatArgs)
    }
}
