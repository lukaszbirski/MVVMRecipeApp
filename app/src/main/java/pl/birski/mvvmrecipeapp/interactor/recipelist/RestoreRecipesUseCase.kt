package pl.birski.mvvmrecipeapp.interactor.recipelist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.birski.mvvmrecipeapp.cache.RecipeDao
import pl.birski.mvvmrecipeapp.domain.data.DataState
import pl.birski.mvvmrecipeapp.domain.mapper.toDomain
import pl.birski.mvvmrecipeapp.domain.model.Recipe
import pl.birski.mvvmrecipeapp.interactor.BaseUseCase
import pl.birski.mvvmrecipeapp.util.RECIPE_PAGINATION_PAGE_SIZE

class RestoreRecipesUseCase(
    private val recipeDao: RecipeDao
) : BaseUseCase<RestoreRecipesUseCase.Params, List<Recipe>>() {

    override fun action(params: Params): Flow<DataState<List<Recipe>>> = flow {
        emit(DataState.loading())

        val cacheResult = if (params.query.isBlank()) {
            recipeDao.getAllRecipes(
                pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                page = params.page
            )
        } else {
            recipeDao.searchRecipes(
                query = params.query,
                pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                page = params.page
            )
        }

        emit(DataState.success(cacheResult.map { it.toDomain() }))
    }

    data class Params(val page: Int, val query: String)
}
