package com.example.pokedex.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pokedex.PokedexApplication
import com.example.pokedex.data.PokemonRepository
import com.example.pokedex.model.Pokemon
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface PokedexUiState {
    data class Success(val pokemons: List<Pokemon>) : PokedexUiState
    object Error : PokedexUiState
    object Loading : PokedexUiState
}

class PokemonViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {
    var pokedexUiState : PokedexUiState by mutableStateOf(PokedexUiState.Loading)
        private set

    init {
        getAll()
    }

    fun getAll(){
        viewModelScope.launch {
            pokedexUiState = PokedexUiState.Loading
            pokedexUiState = try {
                PokedexUiState.Success(pokemonRepository.getAll())
            } catch (e: IOException) {
                e.printStackTrace()
                PokedexUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                PokedexUiState.Error
            }
        }
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PokedexApplication)
                val pokemonRepository = application.container.pokemonRepository
                PokemonViewModel(pokemonRepository = pokemonRepository)
            }
        }
    }
}