package android.kurs.foodrecipes.di

import tj.tajsoft.data.api.meal.MealApi
import tj.tajsoft.data.api.home.HomeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideMealApi(@Named("ApiServiceOne") retrofit: Retrofit) = retrofit.create(MealApi::class.java)
    @Provides
    @Singleton
    fun provideHomeApi( @Named("ApiServiceTwo") retrofit: Retrofit) = retrofit.create(HomeApi::class.java)
}