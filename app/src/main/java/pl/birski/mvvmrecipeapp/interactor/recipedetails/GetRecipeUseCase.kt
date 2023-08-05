package pl.birski.mvvmrecipeapp.interactor.recipedetails

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.birski.mvvmrecipeapp.cache.RecipeDao
import pl.birski.mvvmrecipeapp.domain.data.DataState
import pl.birski.mvvmrecipeapp.domain.mapper.toDomain
import pl.birski.mvvmrecipeapp.domain.mapper.toRecipeEntity
import pl.birski.mvvmrecipeapp.domain.model.Recipe
import pl.birski.mvvmrecipeapp.interactor.BaseUseCase
import pl.birski.mvvmrecipeapp.repository.RecipeRepository

class GetRecipeUseCase(
    private val recipeDao: RecipeDao,
    private val recipeRepository: RecipeRepository
) : BaseUseCase<GetRecipeUseCase.Params, Recipe>() {

    override fun action(params: Params): Flow<DataState<Recipe>> = flow {
        emit(DataState.loading())

        var recipe = recipeDao.getRecipeById(params.recipeId)?.toDomain()

        if (recipe != null) {
            emit(DataState(recipe))
        } else {
            if (params.isNetworkAvailable) {
                val networkRecipe = recipeRepository.get(params.recipeId)
                recipeDao.insertRecipe(networkRecipe.toRecipeEntity())
            }

            recipe = recipeDao.getRecipeById(id = params.recipeId)?.toDomain()
            recipe?.let { emit(DataState(it)) } ?: throw Exception("Unable to get recipe from the cache.")
        }
    }

    data class Params(val recipeId: Int, val isNetworkAvailable: Boolean)
}
