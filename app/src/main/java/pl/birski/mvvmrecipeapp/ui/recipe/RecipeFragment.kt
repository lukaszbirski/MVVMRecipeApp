package pl.birski.mvvmrecipeapp.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.birski.mvvmrecipeapp.ui.BaseApplication
import pl.birski.mvvmrecipeapp.ui.components.RecipeView
import pl.birski.mvvmrecipeapp.ui.components.ShimmerRecipeDetails
import pl.birski.mvvmrecipeapp.ui.theme.AppTheme
import javax.inject.Inject

const val IMAGE_HEIGHT = 260

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let { recipeId ->
            viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                RecipeScreen()
            }
        }
    }

    @Composable
    fun RecipeScreen() {
        val loading = viewModel.loading.value
        val recipe = viewModel.recipe.value
        val scaffoldState = rememberScaffoldState()
        val scrollState = rememberScrollState()

        AppTheme(
            darkTheme = application.isDark.value,
            displayProgressBar = loading
        ) {
            Scaffold(
                scaffoldState = scaffoldState,
                snackbarHost = {
                    scaffoldState.snackbarHostState
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
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

    @Preview(showBackground = true)
    @Composable
    fun RecipePreview() {
        AppTheme(displayProgressBar = true) {
            RecipeScreen()
        }
    }
}
