package com.example.pokedex.network

import com.example.pokedex.model.Pokemon
import retrofit2.http.GET
import retrofit2.http.Path

interface PokedexApiService {
    @GET("pokemons.json")
    suspend fun getAllPokemons(): List<Pokemon>
}