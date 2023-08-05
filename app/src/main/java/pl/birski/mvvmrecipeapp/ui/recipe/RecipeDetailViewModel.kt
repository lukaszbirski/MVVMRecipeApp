package pl.birski.mvvmrecipeapp.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pl.birski.mvvmrecipeapp.domain.model.Recipe
import pl.birski.mvvmrecipeapp.interactor.recipedetails.GetRecipeUseCase
import pl.birski.mvvmrecipeapp.ui.util.DialogQueue
import pl.birski.mvvmrecipeapp.ui.util.InternetConnectionManager
import pl.birski.mvvmrecipeapp.util.TAG
import javax.inject.Inject

const val STATE_KEY_RECIPE = "recipe.state.key.selected_recipeId"

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getRecipeUseCase: GetRecipeUseCase,
    private val internetConnectionManager: InternetConnectionManager
) : ViewModel() {

    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading = mutableStateOf(false)
    val onLoad = mutableStateOf(false)
    val dialogQueue = DialogQueue()

    init {
        savedStateHandle.get<Int>(STATE_KEY_RECIPE)?.let {
            onTriggerEvent(RecipeDetailEvent.GetRecipeDetailEvent(it))
        }
    }

    fun onTriggerEvent(event: RecipeDetailEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is RecipeDetailEvent.GetRecipeDetailEvent -> {
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

    private fun getRecipe(id: Int) {
        getRecipeUseCase.invoke(
            GetRecipeUseCase.Params(
                recipeId = id,
                isNetworkAvailable = internetConnectionManager.isNetworkAvailable.value
            )
        ).onEach {
            loading.value = it.loading
            it.data?.let { recipe ->
                this.recipe.value = recipe
                savedStateHandle[STATE_KEY_RECIPE] = recipe.id
            }
            it.error?.let { error ->
                dialogQueue.appendErrorMessage("Error", error)
                Log.e(TAG, "getRecipe: $error")
            }
        }.launchIn(viewModelScope)
    }
}
