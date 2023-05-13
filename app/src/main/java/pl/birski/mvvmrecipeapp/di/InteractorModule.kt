package pl.birski.mvvmrecipeapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pl.birski.mvvmrecipeapp.cache.database.AppDatabase
import pl.birski.mvvmrecipeapp.interactor.recipedetails.GetRecipeUseCase
import pl.birski.mvvmrecipeapp.interactor.recipelist.RestoreRecipeUseCase
import pl.birski.mvvmrecipeapp.interactor.recipelist.SearchRecipeUseCase
import pl.birski.mvvmrecipeapp.repository.RecipeRepository

@Module
@InstallIn(ViewModelComponent::class)
object InteractorModule {

    @ViewModelScoped
    @Provides
    fun provideSearchRecipeUseCase(db: AppDatabase, recipeRepository: RecipeRepository) =
        SearchRecipeUseCase(recipeDao = db.recipeDao(), recipeRepository = recipeRepository)

    @ViewModelScoped
    @Provides
    fun provideRestoreRecipeUseCase(db: AppDatabase) =
        RestoreRecipeUseCase(recipeDao = db.recipeDao())

    @ViewModelScoped
    @Provides
    fun provideGetRecipeUseCase(db: AppDatabase, recipeRepository: RecipeRepository) =
        GetRecipeUseCase(recipeDao = db.recipeDao(), recipeRepository = recipeRepository)
}
