package com.example.pokedex.fake

import com.example.pokedex.data.PokemonRepository
import com.example.pokedex.model.Pokemon

class FakeNetworkPokemonRepository : PokemonRepository {
    override suspend fun getAll(): List<Pokemon> = FakeDataSource.pokemons
}