package pl.birski.mvvmrecipeapp.ui.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.birski.mvvmrecipeapp.R
import pl.birski.mvvmrecipeapp.ui.BaseApplication
import pl.birski.mvvmrecipeapp.ui.components.CircularProgressBar
import pl.birski.mvvmrecipeapp.ui.components.FoodCategoryChip
import pl.birski.mvvmrecipeapp.ui.components.RecipeCard
import pl.birski.mvvmrecipeapp.ui.components.ShimmerRecipeCard
import pl.birski.mvvmrecipeapp.ui.theme.AppTheme
import javax.inject.Inject

private const val SHIMMERING_RECIPE_LIST_SIZE = 10

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme(darkTheme = application.isDark.value) {
                    val recipes = viewModel.recipes.value
                    val query = viewModel.query.value
                    val selectedCategory = viewModel.selectedCategory.value
                    val keyboardController = LocalSoftwareKeyboardController.current
                    val loading = viewModel.loading.value
                    Column {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colors.surface,
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
                                                viewModel.newSearch()
                                                keyboardController?.hide()
                                            }
                                        ),
                                        leadingIcon = {
                                            Icon(
                                                imageVector = Icons.Filled.Search,
                                                contentDescription = stringResource(id = R.string.search_text)
                                            )
                                        },
                                        textStyle = MaterialTheme.typography.button,
                                        colors = TextFieldDefaults.textFieldColors(
                                            backgroundColor = MaterialTheme.colors.surface
                                        )
                                    )
                                    ConstraintLayout(
                                        modifier = Modifier.align(Alignment.CenterVertically)
                                    ) {
                                        val (menu) = createRefs()
                                        IconButton(
                                            modifier = Modifier
                                                .constrainAs(menu) {
                                                    end.linkTo(parent.end)
                                                    linkTo(top = parent.top, bottom = parent.bottom)
                                                },
                                            onClick = { application.toggleLightTheme() }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.MoreVert,
                                                contentDescription = stringResource(id = R.string.theme_text)
                                            )
                                        }
                                    }
                                }
                                Row(
                                    modifier = Modifier
                                        .horizontalScroll(rememberScrollState())
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp, start = 8.dp)
                                ) {
                                    for (category in getAllFoodCategories()) {
                                        FoodCategoryChip(
                                            category = stringResource(id = category.value),
                                            isSelected = selectedCategory == category,
                                            onExecuteSearch = viewModel::newSearch,
                                            oSelectCategoryChanged = {
                                                viewModel.onSelectedCategoryChanged(it)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                        Box(
                            modifier = Modifier.fillMaxSize()
                                .background(color = MaterialTheme.colors.background)
                        ) {
                            if (loading) {
                                LazyColumn {
                                    items(SHIMMERING_RECIPE_LIST_SIZE) {
                                        ShimmerRecipeCard(imageHeight = 250.dp)
                                    }
                                }
                            } else {
                                LazyColumn {
                                    itemsIndexed(
                                        items = recipes
                                    ) { _, recipe ->
                                        RecipeCard(recipe = recipe, onClick = {})
                                    }
                                }
                            }
                            CircularProgressBar(isDisplayed = loading)
                        }
                    }
                }
            }
        }
    }
}
