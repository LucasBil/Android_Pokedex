package com.example.pokedex

import com.example.pokedex.data.NetworkPokemonRepository
import com.example.pokedex.fake.FakeDataSource
import com.example.pokedex.fake.FakePokedexApiService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NetworkPokemonRepositoryTest {

    @Test
    fun networkPokemonRepository_getAllPokemon_verifyPokemon() =
        runTest {
            val repository = NetworkPokemonRepository(
                pokedexApiService = FakePokedexApiService()
            )
            assertEquals(FakeDataSource.pokemons, repository.getAll())
        }
}