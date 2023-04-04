package pl.birski.mvvmrecipeapp.repository

import pl.birski.mvvmrecipeapp.BuildConfig
import pl.birski.mvvmrecipeapp.domain.mapper.toDomain
import pl.birski.mvvmrecipeapp.domain.model.Recipe
import pl.birski.mvvmrecipeapp.network.service.RecipeService

class RecipeRepositoryImpl(
    private val recipeService: RecipeService
) : RecipeRepository {
    override suspend fun search(page: Int, query: String): List<Recipe> {
        return recipeService.search(
            token = BuildConfig.TOKEN,
            page = page,
            query = query
        ).recipes.map { it.toDomain() }
    }

    override suspend fun get(id: Int): Recipe {
        return recipeService.get(token = BuildConfig.TOKEN, id = id).toDomain()
    }
}
