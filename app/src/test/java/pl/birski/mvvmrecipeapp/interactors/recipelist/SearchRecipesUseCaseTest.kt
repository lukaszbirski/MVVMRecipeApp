package pl.birski.mvvmrecipeapp.interactors.recipelist

import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.birski.mvvmrecipeapp.cache.AppDatabaseFake
import pl.birski.mvvmrecipeapp.cache.RecipeDaoFake
import pl.birski.mvvmrecipeapp.domain.model.Recipe
import pl.birski.mvvmrecipeapp.interactor.recipelist.SearchRecipesUseCase
import pl.birski.mvvmrecipeapp.network.data.MockWebServerResponses.recipeListResponse
import pl.birski.mvvmrecipeapp.network.service.RecipeService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class SearchRecipesUseCaseTest {

    private val appDatabase = AppDatabaseFake()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val DUMMY_TOKEN = "gg335v5453453" // can be anything
    private val DUMMY_QUERY = "This doesn't matter" // can be anything

    // system in test
    private lateinit var searchRecipesUseCase: SearchRecipesUseCase

    // Dependencies
    private lateinit var recipeService: RecipeService
    private lateinit var recipeDao: RecipeDaoFake

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/api/recipe/")
        recipeService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RecipeService::class.java)

        recipeDao = RecipeDaoFake(appDatabaseFake = appDatabase)

        // instantiate the system in test
        searchRecipesUseCase = SearchRecipesUseCase(
            recipeDao = recipeDao,
            recipeService = recipeService
        )
    }

    /**
     * 1. Are the recipes retrieved from the network?
     * 2. Are the recipes inserted into the cache?
     * 3. Are the recipes then emitted as a flow from the cache?
     */
    @Test
    fun getRecipesFromNetwork_emitRecipesFromCache(): Unit = runBlocking {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(recipeListResponse)
        )

        // confirm the cache is empty to start
        assert(recipeDao.getAllRecipes(1, 30).isEmpty())

        val flowItems = searchRecipesUseCase.invoke(SearchRecipesUseCase.Params(1, DUMMY_QUERY, true)).toList()

        // confirm the cache is no longer empty
        assert(recipeDao.getAllRecipes(1, 30).isNotEmpty())

        // first emission should be `loading`
        assert(flowItems[0].loading)

        // Second emission should be the list of recipes
        val recipes = flowItems[1].data
        assert((recipes?.size ?: 0) > 0)

        // confirm they are actually Recipe objects
        assert(recipes?.get(index = 0) is Recipe)

        assert(!flowItems[1].loading) // loading should be false now
    }

    /**
     * Simulate a bad request
     */
    @Test
    fun getRecipesFromNetwork_emitHttpError(): Unit = runBlocking {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )

        val flowItems = searchRecipesUseCase.invoke(SearchRecipesUseCase.Params(1, DUMMY_QUERY, true)).toList()

        // first emission should be `loading`
        assert(flowItems[0].loading)

        // Second emission should be the exception
        val error = flowItems[1].error
        assert(error != null)

        assert(!flowItems[1].loading) // loading should be false now
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
