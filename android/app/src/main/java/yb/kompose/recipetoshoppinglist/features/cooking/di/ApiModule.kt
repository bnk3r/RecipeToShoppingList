package yb.kompose.recipetoshoppinglist.features.cooking.di

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import yb.kompose.recipetoshoppinglist.features.cooking.data.api.service.TheMealDBService
import java.util.concurrent.TimeUnit

fun provideHttpClient(): OkHttpClient = OkHttpClient
    .Builder()
    .readTimeout(60, TimeUnit.SECONDS)
    .connectTimeout(60, TimeUnit.SECONDS)
    .build()

fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

fun provideRetrofitForTheMealDB(
    okHttpClient: OkHttpClient,
    converterFactory: GsonConverterFactory
): Retrofit = Retrofit
    .Builder()
    .baseUrl("https://www.themealdb.com/api/json/v1/1/")
    .client(okHttpClient)
    .addConverterFactory(converterFactory)
    .build()

fun providesTheMealDBService(retrofit: Retrofit): TheMealDBService =
    retrofit.create(TheMealDBService::class.java)

val mealDBApiModule = module {
    single { provideHttpClient() }
    single { provideConverterFactory() }
    single { providesTheMealDBService(get()) }
}