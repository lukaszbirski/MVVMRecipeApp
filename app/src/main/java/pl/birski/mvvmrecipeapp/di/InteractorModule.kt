package pl.birski.mvvmrecipeapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pl.birski.mvvmrecipeapp.cache.database.AppDatabase
import pl.birski.mvvmrecipeapp.interactor.recipedetails.GetRecipeUseCase
import pl.birski.mvvmrecipeapp.interactor.recipelist.RestoreRecipesUseCase
import pl.birski.mvvmrecipeapp.interactor.recipelist.SearchRecipesUseCase
import pl.birski.mvvmrecipeapp.network.service.RecipeService

@Module
@InstallIn(ViewModelComponent::class)
object InteractorModule {

    @ViewModelScoped
    @Provides
    fun provideSearchRecipeUseCase(db: AppDatabase, recipeService: RecipeService) =
        SearchRecipesUseCase(recipeDao = db.recipeDao(), recipeService = recipeService)

    @ViewModelScoped
    @Provides
    fun provideRestoreRecipeUseCase(db: AppDatabase) =
        RestoreRecipesUseCase(recipeDao = db.recipeDao())

    @ViewModelScoped
    @Provides
    fun provideGetRecipeUseCase(db: AppDatabase, recipeService: RecipeService) =
        GetRecipeUseCase(recipeDao = db.recipeDao(), recipeService = recipeService)
}
