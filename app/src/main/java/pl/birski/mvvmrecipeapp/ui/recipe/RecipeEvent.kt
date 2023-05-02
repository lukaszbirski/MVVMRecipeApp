package pl.birski.mvvmrecipeapp.ui.recipe

sealed class RecipeEvent {
    data class GetRecipeEvent(
        val id: Int
    ) : RecipeEvent()
}
