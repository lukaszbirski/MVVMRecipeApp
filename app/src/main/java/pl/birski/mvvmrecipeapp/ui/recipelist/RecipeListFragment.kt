package pl.birski.mvvmrecipeapp.ui.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.birski.mvvmrecipeapp.R
import pl.birski.mvvmrecipeapp.ui.components.RecipeCard

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    private val viewModel: RecipeListViewModel by viewModels()

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val recipes = viewModel.recipes.value
                val query = viewModel.query.value
                val keyboardController = LocalSoftwareKeyboardController.current
                Column {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.primary,
                        elevation = 8.dp
                    ) {
                        Column {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                TextField(
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .padding(8.dp),
                                    value = query,
                                    onValueChange = { viewModel.onQueryChanged(it) },
                                    label = {
                                        Text(text = stringResource(id = R.string.search_text))
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Done
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            viewModel.newSearch(query)
                                            keyboardController?.hide()
                                        }
                                    ),
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Filled.Search,
                                            contentDescription = stringResource(id = R.string.search_text)
                                        )
                                    },
                                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = MaterialTheme.colors.surface
                                    )
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .horizontalScroll(rememberScrollState())
                                    .fillMaxWidth()
                            ) {
                                for (category in getAllFoodCategories()) {
                                    Text(
                                        text = category.value,
                                        style = MaterialTheme.typography.body2,
                                        color = MaterialTheme.colors.secondary,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                    LazyColumn {
                        itemsIndexed(
                            items = recipes
                        ) { _, recipe ->
                            RecipeCard(recipe = recipe, onClick = {})
                        }
                    }
                }
            }
        }
    }
}
