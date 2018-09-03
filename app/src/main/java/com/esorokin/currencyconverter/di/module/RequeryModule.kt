package com.esorokin.currencyconverter.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import io.requery.Persistable
import io.requery.android.sqlite.DatabaseSource
import io.requery.reactivex.KotlinReactiveEntityStore
import io.requery.sql.KotlinEntityDataStore
import javax.inject.Singleton

@Module
class RequeryModule {
    companion object {
        const val DB_NAME = "currency_converter.db"
        const val DB_VERSION = 1
    }

//    @Provides
//    @Singleton
//    fun provideSource(context: Context): DatabaseSource = DatabaseSource(context, Models.DEFAULT, DB_NAME, DB_VERSION)
//
//    @Provides
//    @Singleton
//    fun provideStore(databaseSource: DatabaseSource): KotlinReactiveEntityStore<Persistable> =
//        KotlinReactiveEntityStore(KotlinEntityDataStore(databaseSource.configuration))
}
