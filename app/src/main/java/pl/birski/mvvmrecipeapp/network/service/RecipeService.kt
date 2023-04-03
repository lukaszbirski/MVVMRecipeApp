package pl.birski.mvvmrecipeapp.network.service

import pl.birski.mvvmrecipeapp.network.model.RecipeResponse
import pl.birski.mvvmrecipeapp.network.model.RecipeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RecipeService {

    @GET("search")
    suspend fun search(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): RecipeSearchResponse

    @GET("get")
    suspend fun get(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): RecipeResponse
}
