package ru.agafonovilya.pokeapiapp.model.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.agafonovilya.pokeapiapp.model.entity.api.Pokemon
import ru.agafonovilya.pokeapiapp.model.entity.api.PokemonList

interface PokeApiService {

    /**
     * Get pokemon by name
     */
    @GET("pokemon/{name}")
    suspend fun getPokemonByName(
        @Path("name") name: String
    ): Pokemon


    /**
     * Get pokemon by ID
     */
    @GET("pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id: Int
    ): Pokemon

    /**
     * Get total count and name
     */
    @GET("pokemon-species/")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = LIMIT_FOR_GET_TOTAL_COUNT
    ): PokemonList

    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"
        private const val LIMIT_FOR_GET_TOTAL_COUNT = 0

        fun create(): PokeApiService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PokeApiService::class.java)
        }
    }
}