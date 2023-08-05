package pl.birski.mvvmrecipeapp.ui.recipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.birski.mvvmrecipeapp.ui.components.RecipeView
import pl.birski.mvvmrecipeapp.ui.components.ShimmerRecipeDetails
import pl.birski.mvvmrecipeapp.ui.theme.AppTheme

const val IMAGE_HEIGHT = 260

@Composable
fun RecipeDetailScreen(
    isDarkTheme: Boolean,
    recipeId: Int?,
    viewModel: RecipeViewModel
) {
    val loading = viewModel.loading.value
    val recipe = viewModel.recipe.value
    val dialogQueue = viewModel.dialogQueue
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()

    recipeId?.let { id ->
        val onLoad = viewModel.onLoad.value
        if (!onLoad) {
            viewModel.onLoad.value = true
            viewModel.onTriggerEvent(RecipeDetailEvent.GetRecipeDetailEvent(id))
        }
    }

    AppTheme(
        darkTheme = isDarkTheme,
        displayProgressBar = loading,
        dialogQueue = dialogQueue.queue.value
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = { scaffoldState.snackbarHostState }
        ) { paddingValue ->
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValue)
            ) {
                if (loading && recipe == null) {
                    ShimmerRecipeDetails(imageHeight = IMAGE_HEIGHT.dp)
                } else {
                    recipe?.let {
                        RecipeView(
                            recipe = it,
                            scrollState = scrollState,
                            imageHeight = IMAGE_HEIGHT
                        )
                    }
                }
            }
        }
    }
}
