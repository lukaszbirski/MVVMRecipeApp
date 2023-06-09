package pl.birski.mvvmrecipeapp.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.birski.mvvmrecipeapp.ui.navigation.Screen
import pl.birski.mvvmrecipeapp.ui.recipe.RecipeDetailScreen
import pl.birski.mvvmrecipeapp.ui.recipelist.RecipeListScreen

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.RecipeList.route,
                    builder = {
                        addRecipeListScreen(navController = navController)
                        addRecipeDetailsScreen()
                    }
                )
            }
        }
    }

    private fun NavGraphBuilder.addRecipeListScreen(
        navController: NavController
    ) {
        composable(
            route = Screen.RecipeList.route
        ) {
            RecipeListScreen(
                isDarkTheme = (application as BaseApplication).isDark.value,
                onToggleTheme = { (application as BaseApplication)::toggleLightTheme },
                onNavigateToRecipeDetailScreen = {
                    val route = Screen.RecipeDetail.route + "/${it.id}"
                    navController.navigate(route)
                },
                viewModel = hiltViewModel()
            )
        }
    }

    private fun NavGraphBuilder.addRecipeDetailsScreen() {
        composable(
            route = Screen.RecipeDetail.route + "/{recipeId}",
            arguments = Screen.RecipeDetail.arguments
        ) {
            RecipeDetailScreen(
                isDarkTheme = (application as BaseApplication).isDark.value,
                recipeId = it.arguments?.getInt("recipeId"),
                viewModel = hiltViewModel()
            )
        }
    }
}
