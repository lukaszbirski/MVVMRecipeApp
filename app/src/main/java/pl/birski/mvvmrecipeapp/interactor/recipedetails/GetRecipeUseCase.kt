package pl.birski.mvvmrecipeapp.interactor.recipedetails

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.birski.mvvmrecipeapp.BuildConfig
import pl.birski.mvvmrecipeapp.cache.RecipeDao
import pl.birski.mvvmrecipeapp.domain.data.DataState
import pl.birski.mvvmrecipeapp.domain.mapper.toDomain
import pl.birski.mvvmrecipeapp.domain.mapper.toRecipeEntity
import pl.birski.mvvmrecipeapp.domain.model.Recipe
import pl.birski.mvvmrecipeapp.interactor.BaseUseCase
import pl.birski.mvvmrecipeapp.network.service.RecipeService
import pl.birski.mvvmrecipeapp.util.DateUtil

class GetRecipeUseCase(
    private val recipeDao: RecipeDao,
    private val recipeService: RecipeService
) : BaseUseCase<GetRecipeUseCase.Params, Recipe>() {

    override fun action(params: Params): Flow<DataState<Recipe>> = flow {
        emit(DataState.loading())

        var recipe = recipeDao.getRecipeById(params.recipeId)?.toDomain()

        if (recipe != null) {
            emit(DataState(recipe))
        } else {
            if (params.isNetworkAvailable) {
                val newRecipe = recipeService.get(token = BuildConfig.TOKEN, params.recipeId).toDomain()
                val currentDate = DateUtil.createTimestamp()
                recipeDao.insertRecipe(newRecipe.toRecipeEntity(currentDate))
            }

            recipe = recipeDao.getRecipeById(id = params.recipeId)?.toDomain()
            recipe?.let { emit(DataState(it)) } ?: throw Exception("Unable to get recipe from the cache.")
        }
    }

    data class Params(val recipeId: Int, val isNetworkAvailable: Boolean)
}
