package pl.birski.mvvmrecipeapp.network.model

import com.google.gson.annotations.SerializedName

data class RecipeDTO(
    @SerializedName("pk")
    val pk: Int,
    @SerializedName("title")
    val title: String?,
    @SerializedName("publisher")
    val publisher: String?,
    @SerializedName("featured_image")
    val featuredImage: String?,
    @SerializedName("rating")
    val rating: Int?,
    @SerializedName("source_url")
    val sourceUrl: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("cooking_instructions")
    val cookingInstructions: String?,
    @SerializedName("ingredients")
    val ingredients: List<String>?,
    @SerializedName("date_added")
    val dateAddedString: String?,
    @SerializedName("date_updated")
    val dateUpdatedString: String?,
    @SerializedName("long_date_added")
    val dateAdded: Long?,
    @SerializedName("long_date_updated")
    val dateUpdated: Long?
)
