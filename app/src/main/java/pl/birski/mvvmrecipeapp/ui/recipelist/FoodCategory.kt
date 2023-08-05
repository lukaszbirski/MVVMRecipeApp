package pl.birski.mvvmrecipeapp.ui.recipelist

import androidx.annotation.StringRes
import pl.birski.mvvmrecipeapp.R

enum class FoodCategory(@StringRes val value: Int) {
    ERROR(R.string.error_text),
    CHICKEN(R.string.chicken_text),
    BEEF(R.string.beef_text),
    SOUP(R.string.soup_text),
    DESSERT(R.string.dessert_text),
    VEGETARIAN(R.string.vegetarian_text),
    MILK(R.string.milk_text),
    VEGAN(R.string.vegan_text),
    PIZZA(R.string.pizza_text),
    DONUT(R.string.donut_text),
}

fun getAllFoodCategories() = FoodCategory.values().toList()

fun getFoodCategory(value: String) =
    FoodCategory.values().find { it.name.lowercase() == value.lowercase() }
