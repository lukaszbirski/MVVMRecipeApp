package pl.birski.mvvmrecipeapp.interactor.recipelist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.birski.mvvmrecipeapp.cache.RecipeDao
import pl.birski.mvvmrecipeapp.domain.data.DataState
import pl.birski.mvvmrecipeapp.domain.mapper.toDomain
import pl.birski.mvvmrecipeapp.domain.mapper.toRecipeEntity
import pl.birski.mvvmrecipeapp.domain.model.Recipe
import pl.birski.mvvmrecipeapp.repository.RecipeRepository
import pl.birski.mvvmrecipeapp.util.RECIPE_PAGINATION_PAGE_SIZE

class SearchRecipeUseCase(
    private val recipeDao: RecipeDao,
    private val recipeRepository: RecipeRepository
) {
    fun execute(page: Int, query: String): Flow<DataState<List<Recipe>>> = flow {
        try {
            emit(DataState.loading())

            // todo @lukasz check if there is internet connection
            val recipes = recipeRepository.search(page = page, query = query)

            recipeDao.insertRecipes(recipes.map { it.toRecipeEntity() })

            val cacheResult = if (query.isBlank()) {
                recipeDao.getAllRecipes(
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page
                )
            } else {
                recipeDao.searchRecipes(
                    query = query,
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page
                )
            }
            emit(DataState.success(cacheResult.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }
}
