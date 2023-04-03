package pl.birski.mvvmrecipeapp.repository

import pl.birski.mvvmrecipeapp.domain.mapper.toDomain
import pl.birski.mvvmrecipeapp.domain.model.Recipe
import pl.birski.mvvmrecipeapp.network.service.RecipeService

class RecipeRepositoryImpl(
    private val recipeService: RecipeService
) : RecipeRepository {
    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        return recipeService.search(
            token = token,
            page = page,
            query = query
        ).recipes.map { it.toDomain() }
    }

    override suspend fun get(token: String, id: Int): Recipe {
        return recipeService.get(token = token, id = id).toDomain()
    }
}
