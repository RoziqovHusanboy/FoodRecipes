package android.kurs.foodrecipes.di

import android.kurs.foodrecipes.data.repo.CategoryRepoImpl
import android.kurs.foodrecipes.data.repo.FilterAreaRepositoryImpl
import android.kurs.foodrecipes.domain.repo.CategoryRepository
import android.kurs.foodrecipes.domain.repo.FilterAreaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    abstract fun  bindCategoryRepo(
        categoryRepoImpl: CategoryRepoImpl
    ) : CategoryRepository

    @Binds
    abstract fun  bindFilterAreaRepo(
        filterAreaRepositoryImpl: FilterAreaRepositoryImpl
    ) : FilterAreaRepository
}