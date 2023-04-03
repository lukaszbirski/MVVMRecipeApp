package pl.birski.mvvmrecipeapp.network.model

import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val recipes: List<RecipeResponse>,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: String
)
