package com.example.pokedex.fake

import com.example.pokedex.model.Pokemon
import com.example.pokedex.network.PokedexApiService

class FakePokedexApiService : PokedexApiService {
    override suspend fun getAllPokemons(): List<Pokemon> {
        return FakeDataSource.pokemons;
    }
}