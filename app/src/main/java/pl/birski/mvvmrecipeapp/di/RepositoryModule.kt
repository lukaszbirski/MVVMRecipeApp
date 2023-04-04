package pl.birski.mvvmrecipeapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.birski.mvvmrecipeapp.network.service.RecipeService
import pl.birski.mvvmrecipeapp.repository.RecipeRepository
import pl.birski.mvvmrecipeapp.repository.RecipeRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(recipeService: RecipeService) =
        RecipeRepositoryImpl(recipeService) as RecipeRepository
}
