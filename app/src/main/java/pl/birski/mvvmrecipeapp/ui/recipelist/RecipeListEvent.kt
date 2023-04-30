package pl.birski.mvvmrecipeapp.ui.recipelist

sealed class RecipeListEvent {
    object NewSearchEvent : RecipeListEvent()
    object NextPageEvent : RecipeListEvent()
    object RestoreStateEvent : RecipeListEvent()
}
