package pl.birski.mvvmrecipeapp.ui.recipelist

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
import pl.birski.mvvmrecipeapp.interactor.recipelist.RestoreRecipesUseCase
import pl.birski.mvvmrecipeapp.interactor.recipelist.SearchRecipesUseCase
import pl.birski.mvvmrecipeapp.ui.util.DialogQueue
import pl.birski.mvvmrecipeapp.ui.util.InternetConnectionManager
import pl.birski.mvvmrecipeapp.util.RECIPE_PAGINATION_PAGE_SIZE
import pl.birski.mvvmrecipeapp.util.TAG
import javax.inject.Inject

const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val searchRecipesUseCase: SearchRecipesUseCase,
    private val restoreRecipesUseCase: RestoreRecipesUseCase,
    private val internetConnectionManager: InternetConnectionManager
) : ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    val loading = mutableStateOf(false)
    val page = mutableStateOf(1)
    val dialogQueue = DialogQueue()
    private var recipeListScrollPosition = 0

    init {
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { setPage(it) }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { setQuery(it) }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { setListScrollPosition(it) }
        savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)
            ?.let { setSelectedCategory(it) }

        if (recipeListScrollPosition != 0) {
            onTriggerEvent(RecipeListEvent.RestoreStateEvent)
        } else {
            onTriggerEvent(RecipeListEvent.NewSearchEvent)
        }
    }

    fun onTriggerEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    RecipeListEvent.NewSearchEvent -> newSearch()
                    RecipeListEvent.NextPageEvent -> nextPage()
                    RecipeListEvent.RestoreStateEvent -> restoreState()
                }
            } catch (e: Exception) {
                Log.e(TAG, "onTriggerEvent: Exception: $e, ${e.cause}")
            }
        }
    }

    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    private fun restoreState() {
        restoreRecipesUseCase.invoke(
            RestoreRecipesUseCase.Params(page = page.value, query = query.value)
        ).onEach {
            loading.value = it.loading
            it.data?.let { list ->
                recipes.value = list
            }
            it.error?.let { error ->
                dialogQueue.appendErrorMessage("Error", error)
                Log.e(TAG, "restoreState: $error")
            }
        }.launchIn(viewModelScope)
    }

    private fun newSearch() {
        resetStateSearch()
        searchRecipesUseCase.invoke(
            SearchRecipesUseCase.Params(
                page = page.value,
                query = query.value,
                isNetworkAvailable = internetConnectionManager.isNetworkAvailable.value
            )
        ).onEach {
            loading.value = it.loading
            it.data?.let { list -> recipes.value = list }
            it.error?.let { error ->
                dialogQueue.appendErrorMessage("Error", error)
                Log.e(TAG, "newSearch: $error")
            }
        }.launchIn(viewModelScope)
    }

    private fun nextPage() {
        if ((recipeListScrollPosition + 1) >= (page.value * RECIPE_PAGINATION_PAGE_SIZE)) {
            incrementPage()
            if (page.value > 1) {
                searchRecipesUseCase.invoke(
                    SearchRecipesUseCase.Params(
                        page = page.value,
                        query = query.value,
                        isNetworkAvailable = internetConnectionManager.isNetworkAvailable.value
                    )
                ).onEach {
                    loading.value = it.loading
                    it.data?.let { list -> appendRecipes(list) }
                    it.error?.let { error ->
                        Log.e(TAG, "nextPage: $error")
                        dialogQueue.appendErrorMessage("Error", error)
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        setListScrollPosition(position = position)
    }

    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun incrementPage() {
        setPage(page.value + 1)
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        setSelectedCategory(newCategory)
        onQueryChanged(category)
    }

    private fun clearSelectedCategory() {
        setSelectedCategory(null)
    }

    private fun resetStateSearch() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.name?.lowercase() != query.value.lowercase()) {
            clearSelectedCategory()
        }
    }

    private fun setListScrollPosition(position: Int) {
        recipeListScrollPosition = position
        savedStateHandle[STATE_KEY_LIST_POSITION] = position
    }

    private fun setPage(page: Int) {
        this.page.value = page
        savedStateHandle[STATE_KEY_PAGE] = page
    }

    private fun setSelectedCategory(category: FoodCategory?) {
        selectedCategory.value = category
        savedStateHandle[STATE_KEY_SELECTED_CATEGORY] = category
    }

    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle[STATE_KEY_QUERY] = query
    }
}
