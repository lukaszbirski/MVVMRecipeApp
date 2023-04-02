package pl.birski.mvvmrecipeapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Recipe(
    val id: Int,
    val title: String,
    val publisher: String,
    val featuredImage: String,
    val rating: Int,
    val sourceUrl: String,
    val description: String,
    val cookingInstructions: String,
    val ingredients: List<String> = listOf(),
    val dateAdded: Date?,
    val dateUpdated: Date?
) : Parcelable
