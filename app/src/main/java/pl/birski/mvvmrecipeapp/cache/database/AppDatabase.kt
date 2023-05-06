package pl.birski.mvvmrecipeapp.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.birski.mvvmrecipeapp.cache.RecipeDao
import pl.birski.mvvmrecipeapp.cache.model.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object {
        val DATABASE_NAME: String = "recipe_db"
    }
}
