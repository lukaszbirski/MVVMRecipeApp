package pl.birski.mvvmrecipeapp.ui.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.birski.mvvmrecipeapp.R
import pl.birski.mvvmrecipeapp.domain.model.Recipe
import pl.birski.mvvmrecipeapp.ui.recipelist.PAGE_SIZE
import pl.birski.mvvmrecipeapp.ui.recipelist.RecipeListEvent

private const val SHIMMERING_RECIPE_LIST_SIZE = 10

@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    onNextPage: (RecipeListEvent) -> Unit,
    page: Int,
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        if (loading && recipes.isEmpty()) {
            LazyColumn {
                items(SHIMMERING_RECIPE_LIST_SIZE) {
                    ShimmerRecipeCard(imageHeight = 250.dp)
                }
            }
        } else {
            LazyColumn {
                itemsIndexed(
                    items = recipes
                ) { index, recipe ->
                    onChangeRecipeScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                        onNextPage(RecipeListEvent.NextPageEvent)
                    }
                    RecipeCard(
                        recipe = recipe,
                        onClick = {
                            val bundle = Bundle()
                            bundle.putInt("recipeId", recipe.id)
                            navController.navigate(
                                R.id.action_recipeListFragment_to_recipeFragment,
                                bundle
                            )
                        }
                    )
                }
            }
        }
    }
}
