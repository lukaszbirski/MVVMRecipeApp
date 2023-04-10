package pl.birski.mvvmrecipeapp.ui.recipelist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.birski.mvvmrecipeapp.domain.model.Recipe
import pl.birski.mvvmrecipeapp.repository.RecipeRepository
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    init {
        newSearch()
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun newSearch() {
        viewModelScope.launch {
            loading.value = true
            resetStateSearch()
            val result = repository.search(page = 1, query = query.value)
            recipes.value = result
            loading.value = false
        }
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
        if (selectedCategory.value?.name?.lowercase() != query.value.lowercase()) {
            clearSelectedCategory()
        }
    }
}
