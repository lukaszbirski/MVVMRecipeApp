package pl.birski.mvvmrecipeapp.ui.recipelist

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.birski.mvvmrecipeapp.domain.model.Recipe
import pl.birski.mvvmrecipeapp.repository.RecipeRepository
import pl.birski.mvvmrecipeapp.util.TAG
import javax.inject.Inject

const val PAGE_SIZE = 30

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    val loading = mutableStateOf(false)
    val page = mutableStateOf(1)
    private var recipeListScrollPosition = 0

    init {
        onTriggerEvent(RecipeListEvent.NewSearchEvent)
    }

    fun onTriggerEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    RecipeListEvent.NewSearchEvent -> newSearch()
                    RecipeListEvent.NextPageEvent -> nextPage()
                }
            } catch (e: Exception) {
                Log.e(TAG, "onTriggerEvent: Exception: $e, ${e.cause}")
            }
        }
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    private suspend fun newSearch() {
        loading.value = true
        resetStateSearch()
        val result = repository.search(page = 1, query = query.value)
        recipes.value = result
        loading.value = false
    }

    private suspend fun nextPage() {
        if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            loading.value = true
            incrementPage()
            if (page.value > 1) {
                val result = repository.search(page = page.value, query = query.value)
                appendRecipes(result)
            }
            loading.value = false
        }
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        recipeListScrollPosition = position
    }

    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun incrementPage() {
        page.value = page.value + 1
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    private fun resetStateSearch() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.name?.lowercase() != query.value.lowercase()) {
            clearSelectedCategory()
        }
    }
}
