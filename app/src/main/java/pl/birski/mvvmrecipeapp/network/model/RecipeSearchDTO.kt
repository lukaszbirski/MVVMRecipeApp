package pl.birski.mvvmrecipeapp.network.model

import com.google.gson.annotations.SerializedName

data class RecipeSearchDTO(
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val recipes: List<RecipeDTO>,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: String
)
