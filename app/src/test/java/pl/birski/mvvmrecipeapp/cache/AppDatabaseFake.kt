package pl.birski.mvvmrecipeapp.cache

import pl.birski.mvvmrecipeapp.cache.model.RecipeEntity

class AppDatabaseFake {
    val recipes = mutableListOf<RecipeEntity>()
}
