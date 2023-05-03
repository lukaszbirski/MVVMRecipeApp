package pl.birski.mvvmrecipeapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import pl.birski.mvvmrecipeapp.R
import pl.birski.mvvmrecipeapp.domain.model.Recipe
import pl.birski.mvvmrecipeapp.util.DateUtil

const val IMAGE_HEIGHT = 260

@OptIn(ExperimentalCoilApi::class)
@Composable
fun RecipeView(
    recipe: Recipe,
    scrollState: ScrollState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Image(
            painter = recipe.featuredImage?.let {
                rememberImagePainter(it)
            } ?: painterResource(id = R.drawable.empty_plate),
            contentDescription = stringResource(id = R.string.recipe_view_text),
            modifier = Modifier
                .fillMaxWidth()
                .height(IMAGE_HEIGHT.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = recipe.title,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.h3
                )
                val rank = recipe.rating.toString()
                Text(
                    text = rank,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End)
                        .align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.h5
                )
            }
            Text(
                text = getUpdatedText(recipe = recipe),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                style = MaterialTheme.typography.caption
            )
            for (ingredient in recipe.ingredients) {
                Text(
                    text = ingredient,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Composable
private fun getUpdatedText(recipe: Recipe) = recipe.dateUpdated?.let {
    stringResource(R.string.updated_text, DateUtil.dateToSlashText(it), recipe.publisher)
} ?: stringResource(R.string.update_by_text, recipe.publisher)
