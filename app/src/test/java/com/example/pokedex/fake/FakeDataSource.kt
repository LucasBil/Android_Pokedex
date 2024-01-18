package com.example.pokedex.fake

import com.example.pokedex.model.Pokemon

object FakeDataSource {
    private val bulbizard = Pokemon(
        id = 1,
        name = "Bulbizard",
        types = listOf("grass", "poison"),
        summary = "A summary",
        sprite = "bulbizard.png",
        family = listOf("vilvizard")
    )

    private val charmander = Pokemon(
        id = 2,
        name = "Charmander",
        types = listOf("fire"),
        summary = "A summary for Charmander",
        sprite = "charmander.png",
        family = listOf("charmeleon", "charizard")
    )

    private val squirtle = Pokemon(
        id = 3,
        name = "Squirtle",
        types = listOf("water"),
        summary = "A summary for Squirtle",
        sprite = "squirtle.png",
        family = listOf("wartortle", "blastoise")
    )

    private val pikachu = Pokemon(
        id = 4,
        name = "Pikachu",
        types = listOf("electric"),
        summary = "A summary for Pikachu",
        sprite = "pikachu.png",
        family = listOf("raichu")
    )

    // Add more Pokémon as needed

    val pokemons = listOf(
        bulbizard,
        charmander,
        squirtle,
        pikachu
        // Add more Pokémon here
    )
}