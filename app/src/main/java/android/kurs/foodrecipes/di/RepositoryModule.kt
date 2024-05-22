package android.kurs.foodrecipes.di

import android.kurs.foodrecipes.data.repo.ProfileSaveRepoIml
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tj.tajsoft.data.repo.HomeRepoImpl
import tj.tajsoft.data.repo.MealRepoImpl
import tj.tajsoft.domain.repo.HomeRepository
import tj.tajsoft.domain.repo.MealRepository
import tj.tajsoft.domain.repo.ProfileSaveRepo

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    abstract fun  bindMealRepo(
        mealRepoImpl: MealRepoImpl
    ) : MealRepository

    @Binds
    abstract fun  bindHomeRepository(
        homeRepoImpl: HomeRepoImpl
    ) : HomeRepository

    @Binds
    abstract fun  bindProfileRepository(
        profileSaveRepoIml: ProfileSaveRepoIml
    ) : ProfileSaveRepo
}