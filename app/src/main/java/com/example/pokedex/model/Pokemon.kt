package com.example.pokedex.model

import androidx.compose.ui.graphics.Color
import com.example.pokedex.R
import com.example.pokedex.ui.theme.bugType
import com.example.pokedex.ui.theme.darkType
import com.example.pokedex.ui.theme.dragonType
import com.example.pokedex.ui.theme.electricType
import com.example.pokedex.ui.theme.fairyType
import com.example.pokedex.ui.theme.fightingType
import com.example.pokedex.ui.theme.fireType
import com.example.pokedex.ui.theme.flyingType
import com.example.pokedex.ui.theme.ghostType
import com.example.pokedex.ui.theme.grassType
import com.example.pokedex.ui.theme.groundType
import com.example.pokedex.ui.theme.iceType
import com.example.pokedex.ui.theme.normalType
import com.example.pokedex.ui.theme.poisonType
import com.example.pokedex.ui.theme.psychicType
import com.example.pokedex.ui.theme.rockType
import com.example.pokedex.ui.theme.steelType
import com.example.pokedex.ui.theme.waterType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    @SerialName(value = "id")
    val id: Int,
    @SerialName(value = "name")
    val name: String,
    @SerialName(value = "types")
    val types : List<String>,
    @SerialName(value = "summary")
    val summary : String,
    @SerialName(value = "sprite")
    val sprite : String,
    @SerialName(value = "family")
    val family : List<String>
){
    fun getTypesList() : List<Type>{
        return this.types.map { typeString ->
            when (typeString) {
                Type.Grass.toNormalizeString() -> Type.Grass
                Type.Fire.toNormalizeString() -> Type.Fire
                Type.Water.toNormalizeString() -> Type.Water
                Type.Fighting.toNormalizeString() -> Type.Fighting
                Type.Flying.toNormalizeString() -> Type.Flying
                Type.Poison.toNormalizeString() -> Type.Poison
                Type.Ground.toNormalizeString() -> Type.Ground
                Type.Rock.toNormalizeString() -> Type.Rock
                Type.Bug.toNormalizeString() -> Type.Bug
                Type.Steel.toNormalizeString() -> Type.Steel
                Type.Ghost.toNormalizeString() -> Type.Normal
                Type.Electric.toNormalizeString() -> Type.Electric
                Type.Psychic.toNormalizeString() -> Type.Psychic
                Type.Ice.toNormalizeString() -> Type.Ice
                Type.Dragon.toNormalizeString() -> Type.Dragon
                Type.Dark.toNormalizeString() -> Type.Dark
                Type.Fairy.toNormalizeString() ->Type.Fairy
                Type.Normal.toNormalizeString() -> Type.Normal
                else -> throw IllegalArgumentException("Unknown type: $typeString")
            }
        }
    }
}

enum class Type {
    Grass,
    Fire,
    Water,
    Fighting,
    Flying,
    Poison,
    Ground,
    Rock,
    Bug,
    Steel,
    Ghost,
    Electric,
    Psychic,
    Ice,
    Dragon,
    Dark,
    Fairy,
    Normal;

    fun toNormalizeString() : String{
        when(this){
            Type.Grass -> return "grass"
            Type.Fire -> return "fire"
            Type.Water -> return "water"
            Type.Fighting -> return "fighting"
            Type.Flying -> return "flying"
            Type.Poison -> return "poison"
            Type.Ground -> return "ground"
            Type.Rock -> return "rock"
            Type.Bug -> return "bug"
            Type.Steel -> return "steel"
            Type.Ghost -> return "ghost"
            Type.Electric -> return "electric"
            Type.Psychic -> return "psychic"
            Type.Ice -> return "ice"
            Type.Dragon -> return "dragon"
            Type.Dark -> return "dark"
            Type.Fairy -> return "fairy"
            Type.Normal -> return "normal"
        }
    }

    fun toIconType() : Int{
        when(this){
            Type.Grass -> return R.drawable.grass
            Type.Fire -> return R.drawable.fire
            Type.Water -> return R.drawable.water
            Type.Fighting -> return R.drawable.fighting
            Type.Flying -> return R.drawable.flying
            Type.Poison -> return R.drawable.poison
            Type.Ground -> return R.drawable.ground
            Type.Rock -> return R.drawable.rock
            Type.Bug -> return R.drawable.bug
            Type.Steel -> return R.drawable.steel
            Type.Ghost -> return R.drawable.ghost
            Type.Electric -> return R.drawable.electric
            Type.Psychic -> return R.drawable.psychic
            Type.Ice -> return R.drawable.ice
            Type.Dragon -> return R.drawable.dragon
            Type.Dark -> return R.drawable.dark
            Type.Fairy -> return R.drawable.fairy
            Type.Normal -> return R.drawable.normal
        }
    }

    fun toColorType() : Color{
        return when (this) {
            Type.Grass -> grassType
            Type.Fire -> fireType
            Type.Water -> waterType
            Type.Fighting -> fightingType
            Type.Flying -> flyingType
            Type.Poison -> poisonType
            Type.Ground -> groundType
            Type.Rock -> rockType
            Type.Bug -> bugType
            Type.Steel -> steelType
            Type.Ghost -> ghostType
            Type.Electric -> electricType
            Type.Psychic -> psychicType
            Type.Ice -> iceType
            Type.Dragon -> dragonType
            Type.Dark -> darkType
            Type.Fairy -> fairyType
            Type.Normal -> normalType
            else -> Color.Red
        }
    }
}