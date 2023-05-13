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

        val recipe = recipeDao.getRecipeById(params.recipeId)?.toDomain()

        if (recipe != null) {
            emit(DataState(recipe))
        } else {
            val networkRecipe = recipeRepository.get(params.recipeId)
            recipeDao.insertRecipe(networkRecipe.toRecipeEntity())
            emit(DataState(networkRecipe))
        }
    }

    data class Params(val recipeId: Int)
}
