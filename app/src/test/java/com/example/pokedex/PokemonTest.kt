package com.example.pokedex

import com.example.pokedex.data.NetworkPokemonRepository
import com.example.pokedex.fake.FakeDataSource
import com.example.pokedex.fake.FakePokedexApiService
import com.example.pokedex.model.Type
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PokemonTest {

    @Test
    fun pokemon_getType() =
        runTest {
            var bulbizard = FakeDataSource.pokemons[0]
            var types = bulbizard.getTypesList()
            TestCase.assertEquals(types, listOf(Type.Grass,Type.Poison))
        }

    @Test
    fun type_getName() =
        runTest {
            TestCase.assertEquals(Type.Grass.toNormalizeString(), "grass")
        }

    @Test
    fun type_getIconNumber() =
        runTest {
            TestCase.assertEquals(Type.Grass.toIconType(), R.drawable.grass)
        }
}