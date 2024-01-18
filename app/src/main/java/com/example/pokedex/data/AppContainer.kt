package com.example.pokedex.data

import com.example.pokedex.network.PokedexApiService
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer {
    val pokemonRepository : PokemonRepository
}

class DefaultAppContainer : AppContainer{
    private val baseUrl = "https://raw.githubusercontent.com/LucasBil/Android_Pokedex/master/data/" //"http://192.168.3.51:5000"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(
        json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrl)
        .build()

    private  val retrofitService: PokedexApiService by lazy {
        retrofit.create(PokedexApiService::class.java)
    }

    override val pokemonRepository: PokemonRepository by lazy {
        NetworkPokemonRepository(retrofitService)
    }
}