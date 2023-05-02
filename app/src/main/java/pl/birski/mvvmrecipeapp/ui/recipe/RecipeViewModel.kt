package pl.birski.mvvmrecipeapp.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.birski.mvvmrecipeapp.domain.model.Recipe
import pl.birski.mvvmrecipeapp.repository.RecipeRepository
import pl.birski.mvvmrecipeapp.util.TAG
import javax.inject.Inject

const val STATE_KEY_RECIPE = "recipe.state.key.selected_recipeId"

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    init {
        savedStateHandle.get<Int>(STATE_KEY_RECIPE)?.let {
            onTriggerEvent(RecipeEvent.GetRecipeEvent(it))
        }
    }

    fun onTriggerEvent(event: RecipeEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is RecipeEvent.GetRecipeEvent -> {
                        if (recipe.value == null) {
                            getRecipe(event.id)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "onTriggerEvent: Exception: $e, ${e.cause}")
            }
        }
    }

    private suspend fun getRecipe(id: Int) {
        loading.value = true
        val recipe = repository.get(id)
        this.recipe.value = recipe
        savedStateHandle[STATE_KEY_RECIPE] = recipe.id
        loading.value = false
    }
}
