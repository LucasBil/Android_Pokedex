package com.example.pokedex

import com.example.pokedex.rules.TestDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class PokemonViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun pokemonViewModel_getPokemonCard_verifyPokemonUiStateSuccess() =
        runTest {
            //val pokedexViewModel = PokedexViewModel(
            //    pokemonCardRepository = FakeNetworkPokemonCardRepository()
            //)
            //assertEquals(
            //    PokedexUiState.Success(FakeDataSource.photosList, FakeDataSource.UserList),
            //    pokedexViewModel.pokedexUiState
            //)
        }
}