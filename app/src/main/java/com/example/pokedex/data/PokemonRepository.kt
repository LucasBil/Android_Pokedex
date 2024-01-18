package com.example.pokedex.data

import com.example.pokedex.model.Pokemon
import com.example.pokedex.network.PokedexApiService

interface PokemonRepository {
    suspend fun getAll() : List<Pokemon>
}

class NetworkPokemonRepository(
    private val pokedexApiService: PokedexApiService
) : PokemonRepository {
    override suspend fun getAll(): List<Pokemon> = pokedexApiService.getAllPokemons()
}