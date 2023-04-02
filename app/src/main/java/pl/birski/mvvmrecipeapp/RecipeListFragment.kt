package pl.birski.mvvmrecipeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.birski.mvvmrecipeapp.ui.theme.MVVMRecipeAppTheme

class RecipeListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                RecipeListScreen()
            }
        }
    }

    @Composable
    fun RecipeListScreen() {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Recipe List Fragment")
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = {
                    findNavController().navigate(R.id.action_recipeListFragment_to_recipeFragment)
                }
            ) {
                Text(text = "TO RECIPE FRAGMENT")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun RecipeListPreview() {
        MVVMRecipeAppTheme {
            RecipeListScreen()
        }
    }
}
