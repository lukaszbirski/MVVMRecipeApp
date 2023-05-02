package pl.birski.mvvmrecipeapp.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.birski.mvvmrecipeapp.ui.theme.AppTheme

@AndroidEntryPoint
class RecipeFragment : Fragment() {

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
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = recipe?.let { recipe.title } ?: "Loading...",
                style = MaterialTheme.typography.h5
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun RecipePreview() {
        AppTheme {
            RecipeScreen()
        }
    }
}
