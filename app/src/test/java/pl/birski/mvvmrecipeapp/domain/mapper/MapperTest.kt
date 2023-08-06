package pl.birski.mvvmrecipeapp.domain.mapper

import org.junit.jupiter.api.Test
import pl.birski.mvvmrecipeapp.cache.model.RecipeEntity
import pl.birski.mvvmrecipeapp.domain.model.Recipe
import pl.birski.mvvmrecipeapp.network.model.RecipeDTO
import pl.birski.mvvmrecipeapp.util.DateUtil
import java.util.Date

class MapperTest {

    @Test
    fun `map recipeEntity to Recipe, entity is correctly mapped`() {
        // given
        val recipeEntity = recipeEntityFake

        // tested
        val result = recipeEntity.toDomain()

        // assert
        assert(result == recipeFake)
    }

    @Test
    fun `map recipeDTO to Recipe, DTO is correctly mapped`() {
        // given
        val recipeDTO = recipeDTOFake

        // tested
        val result = recipeDTO.toDomain()

        // assert
        assert(result == recipeFake)
    }

    @Test
    fun `map recipe to RecipeEntity, recipe is correctly mapped`() {
        // given
        val recipe = recipeFake

        // tested
        val result = recipe.toRecipeEntity(dateCached)

        // assert
        assert(result == recipeEntityFake)
    }

    companion object {
        private const val id = 1
        private const val title = "title"
        private const val publisher = "punlisher"
        private const val featuredImage = "featuredImage"
        private const val rating = 1
        private const val sourceUrl = "sourceUrl"
        private val ingredients = listOf<String>()
        private val dateAdded = Date(1)
        private val dateUpdated = Date(2)
        private val dateCached = DateUtil.createTimestamp()
        private const val longDateAdded = 1L
        private const val longDateUpdated = 2L

        private val recipeFake = Recipe(
            id = id,
            title = title,
            publisher = publisher,
            featuredImage = featuredImage,
            rating = rating,
            sourceUrl = sourceUrl,
            ingredients = ingredients,
            dateAdded = dateAdded,
            dateUpdated = dateUpdated,
        )

        private val recipeEntityFake = RecipeEntity(
            id = id,
            title = title,
            publisher = publisher,
            featuredImage = featuredImage,
            rating = rating,
            sourceUrl = sourceUrl,
            ingredients = ingredients,
            dateAdded = dateAdded,
            dateUpdated = dateUpdated,
            dateCached = dateCached
        )

        private val recipeDTOFake = RecipeDTO(
            pk = id,
            title = title,
            publisher = publisher,
            featuredImage = featuredImage,
            rating = rating,
            sourceUrl = sourceUrl,
            ingredients = ingredients,
            longDateAdded = longDateAdded,
            longDateUpdated = longDateUpdated
        )
    }
}
