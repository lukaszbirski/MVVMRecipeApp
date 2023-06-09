package pl.birski.mvvmrecipeapp.network.model

import com.google.gson.annotations.SerializedName

data class RecipeDTO(
    @SerializedName("pk")
    val pk: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("publisher")
    val publisher: String,
    @SerializedName("featured_image")
    val featuredImage: String,
    @SerializedName("rating")
    val rating: Int = 0,
    @SerializedName("source_url")
    val sourceUrl: String,
    @SerializedName("ingredients")
    var ingredients: List<String> = emptyList(),
    @SerializedName("long_date_added")
    var longDateAdded: Long,
    @SerializedName("long_date_updated")
    var longDateUpdated: Long
)
