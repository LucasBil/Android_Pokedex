package com.example.pokedex.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokedex.R
import com.example.pokedex.model.Pokemon
import com.example.pokedex.ui.ErrorScreen
import com.example.pokedex.ui.LoadingScreen
import com.example.pokedex.ui.theme.PokedexTheme

@Composable
fun DetailScreen(
    pokemon: Pokemon,
    family : List<Pokemon>,
    onSwitchPokemon : (Pokemon) -> Unit,
    modifier: Modifier = Modifier
)
{
    PokedexTheme(darkTheme = isSystemInDarkTheme()) {
        val colorScheme = MaterialTheme.colorScheme
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(5.dp) // Add padding as needed
        ){
            item { TopDetail(pokemon, modifier) }
            item { FightingArea(pokemon, modifier) }
            item {
                Box(
                    modifier = modifier
                        .padding(5.dp)
                        .background(colorScheme.primary)
                        .clip(
                            shape = RoundedCornerShape(
                                topStart = 15.dp, topEnd = 15.dp,
                                bottomStart = 15.dp, bottomEnd = 15.dp
                            )
                        )
                ){
                    Text(
                        text = pokemon.summary,
                        color = colorScheme.onPrimary,
                        modifier = modifier
                            .padding(10.dp)
                    )
                }
            }
            items(family.size) { pokemonId ->
                PokemonCard(family[pokemonId], onSwitchPokemon, modifier)
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

@Composable
fun FightingArea(
    pokemon: Pokemon,
    modifier: Modifier
){
    Box(
        modifier = modifier
            .requiredHeight(288.dp)
    ){
        Image(
            painter = painterResource(id = R.drawable.battleground_background),
            contentDescription = "Arena Battleground",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxSize()
        )
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(pokemon.sprite)
                .crossfade(true).build(),
            error = painterResource(R.drawable.ic_launcher_background),
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = pokemon.name,
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .offset(
                    x = (-25).dp,
                    y = (55).dp
                )
                .requiredHeight(150.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.trainer),
            contentDescription = "Trainer",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(
                    x = 55.dp,
                    y = (5).dp
                )
                .requiredHeight(164.dp)
                .requiredWidth(104.dp)
        )
    }
}

@Composable
fun TopDetail(
    pokemon : Pokemon,
    modifier: Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 119.dp)
            .background(color = pokemon.getTypesList()[0].toColorType())
            .clip(shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
    ){
        Text(
            text = pokemon.name.capitalize(),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Black),
            modifier = Modifier
                .offset(
                    x = 20.dp,
                    y = 10.dp
                )
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
        Text(
            text = String.format("#%04d", pokemon.id),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Black),
            modifier = Modifier
                .offset(
                    x = (-10).dp,
                    y = (-10).dp
                )
                .align(alignment = Alignment.BottomEnd)
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .align(alignment = Alignment.BottomStart)
                .offset(
                    x = 20.dp,
                    y = (-20).dp
                )
                .width(150.dp),
        ) {
            items(items = pokemon.getTypesList()) { type ->
                Image(
                    painter = painterResource(id = type.toIconType()),
                    contentDescription = type.toNormalizeString(),
                    modifier = Modifier
                        .requiredHeight(height = 40.dp)
                        .requiredWidth(width = 40.dp)
                )
            }
        }
    }
}

@Preview(widthDp = 440)
@Composable
fun FightingAreaPreview(){
    val pokemon = Pokemon(
        id = 1,
        name = "venusaur",
        types = listOf("grass","poison"),
        summary = "The plant blooms when it is absorbing solar energy. It stays on the move to seek sunlight",
        sprite = "",
        family = listOf()
    )
    PokedexTheme {
        FightingArea(pokemon,modifier= Modifier)
    }
}

@Preview(widthDp = 440)
@Composable
fun TopDetailPreview(){
    val pokemon = Pokemon(
        id = 1,
        name = "venusaur",
        types = listOf("grass","poison"),
        summary = "The plant blooms when it is absorbing solar energy. It stays on the move to seek sunlight",
        sprite = "",
        family = listOf()
    )
    PokedexTheme {
        TopDetail(pokemon,modifier= Modifier)
    }
}
