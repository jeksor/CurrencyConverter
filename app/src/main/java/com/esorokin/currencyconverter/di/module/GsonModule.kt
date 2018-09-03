package com.esorokin.currencyconverter.di.module

import com.google.gson.*
import dagger.Module
import dagger.Provides
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Module
class GsonModule {
    @Provides
    @Singleton
    fun provideDateDeserializer(): JsonDeserializer<Date> {
        val dateFormats = arrayOf("yyyy-MM-dd")
        return JsonDeserializer { json, _, _ ->
            for (format in dateFormats) {
                try {
                    return@JsonDeserializer SimpleDateFormat(format, Locale.getDefault()).parse(json.asString)
                } catch (ignore: ParseException) {
                    //ignore, try next
                    Timber.d("Format [$format] not correct for \"${json.asString}\". Try next.")
                }

            }
            throw JsonParseException("Unparsable date: \"${json.asString}\". Supported formats: ${arrayListOf(dateFormats)}")
        }
    }

    @Provides
    @Singleton
    fun provideDateSerializer(): JsonSerializer<Date> {
        val dateFormat = "yyyy-MM-dd'T'HH:mm:ss"
        return JsonSerializer { src, _, _ -> JsonPrimitive(SimpleDateFormat(dateFormat, Locale.getDefault()).format(src)) }
    }

    @Provides
    @Singleton
    fun provideGson(dateDeserializer: JsonDeserializer<Date>, dateSerializer: JsonSerializer<Date>): Gson = GsonBuilder()
        .registerTypeAdapter(Date::class.java, dateDeserializer)
        .registerTypeAdapter(Date::class.java, dateSerializer)
        .create()
}
