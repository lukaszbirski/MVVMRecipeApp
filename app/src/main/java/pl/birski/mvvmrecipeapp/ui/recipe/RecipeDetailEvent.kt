package pl.birski.mvvmrecipeapp.ui.recipe

sealed class RecipeDetailEvent {
    data class GetRecipeDetailEvent(
        val id: Int
    ) : RecipeDetailEvent()
}
