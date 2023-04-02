package pl.birski.mvvmrecipeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import pl.birski.mvvmrecipeapp.ui.theme.MVVMRecipeAppTheme

class RecipeFragment : Fragment() {

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
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Recipe Fragment")
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun RecipePreview() {
        MVVMRecipeAppTheme {
            RecipeScreen()
        }
    }
}
