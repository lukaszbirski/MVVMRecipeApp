package pl.birski.mvvmrecipeapp.domain.mapper

import pl.birski.mvvmrecipeapp.cache.model.RecipeEntity
import pl.birski.mvvmrecipeapp.domain.model.Recipe
import pl.birski.mvvmrecipeapp.network.model.RecipeDTO
import pl.birski.mvvmrecipeapp.util.DateUtil
import java.util.Date

fun RecipeDTO.toDomain() = Recipe(
    id = this.pk,
    title = this.title,
    publisher = this.publisher,
    featuredImage = this.featuredImage,
    rating = this.rating,
    sourceUrl = this.sourceUrl,
    ingredients = this.ingredients.map { it },
    dateAdded = DateUtil.longToDate(this.longDateAdded),
    dateUpdated = DateUtil.longToDate(this.longDateUpdated)
)

fun RecipeEntity.toDomain() = Recipe(
    id = this.id,
    title = this.title,
    publisher = this.publisher,
    featuredImage = this.featuredImage,
    rating = this.rating,
    sourceUrl = this.sourceUrl,
    ingredients = this.ingredients,
    dateAdded = this.dateAdded,
    dateUpdated = this.dateUpdated
)

fun Recipe.toRecipeEntity(dateCached: Date) = RecipeEntity(
    id = this.id,
    title = this.title,
    publisher = this.publisher,
    featuredImage = this.featuredImage,
    rating = this.rating,
    sourceUrl = this.sourceUrl,
    ingredients = this.ingredients,
    dateAdded = this.dateAdded,
    dateUpdated = this.dateUpdated,
    dateCached = dateCached
)
