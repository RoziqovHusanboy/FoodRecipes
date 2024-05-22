package android.kurs.foodrecipes.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    const val url = "https://www.themealdb.com/api/json/v1/1/"
    const val urlPosmen = "https://144de399-afd8-4a75-b494-5e6bab1f2ab2.mock.pstmn.io/"

    @Provides
    @Singleton
    @Named("ApiServiceOne")
    fun provideRetrofitOne(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    @Named("ApiServiceTwo")
    fun provideRetrofitTwo(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(urlPosmen)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient()=
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()

}