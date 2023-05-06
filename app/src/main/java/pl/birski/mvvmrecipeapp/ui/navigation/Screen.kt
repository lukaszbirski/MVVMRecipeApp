package pl.birski.mvvmrecipeapp.ui.navigation

sealed class Screen(
    val route: String
) {
    object RecipeList : Screen("recipeList")
    object RecipeDetail : Screen("recipeDetail")
}
