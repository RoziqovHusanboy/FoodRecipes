package android.kurs.foodrecipes.di

import android.kurs.foodrecipes.data.api.categories.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideCategoryApi(retrofit: Retrofit) = retrofit.create(Api::class.java)
}