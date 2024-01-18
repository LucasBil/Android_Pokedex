package com.example.pokedex.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedex.model.Pokemon
import com.example.pokedex.ui.theme.PokedexTheme
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokedex.R
import com.example.pokedex.ui.ErrorScreen
import com.example.pokedex.ui.LoadingScreen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(
    pokemons: List<Pokemon>,
    retryAction: () -> Unit,
    onPokemonCardClick: (Pokemon) -> Unit,
    scrollState: LazyGridState,
    modifier: Modifier = Modifier
)
{
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    PokedexTheme {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = retryAction
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                state = scrollState,
                modifier = modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(5.dp), // Padding on the sides
                verticalArrangement = Arrangement.spacedBy(10.dp) // Spacing between items
            ) {
                items(items = pokemons, key = { pokemon -> pokemon.id }) { pokemon ->
                    PokemonCard(pokemon,{ onPokemonCardClick(pokemon) },modifier.fillMaxWidth())
                }
            }
        }
    }

}

//#region CARD
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonCard(
    pokemon : Pokemon,
    onPokemonCardClick: (Pokemon) -> Unit,
    modifier: Modifier
){
    Card(
        onClick = { onPokemonCardClick(pokemon) },
        modifier = modifier
            .requiredHeight(119.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = pokemon.getTypesList()[0].toColorType())
        ){
            Image(
                painter = painterResource(id = R.drawable.icon_pokeball),
                contentDescription = "Icon pokeball",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .offset(
                        x = (-12).dp,
                        y = 40.dp
                    )
                    .requiredHeight(height = 111.dp)
                )
            Box(
                modifier = Modifier.fillMaxSize()
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
                            y = 10.dp
                        )
                        .align(alignment = Alignment.TopEnd)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )

                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current).data(pokemon.sprite)
                        .crossfade(true).build(),
                    error = painterResource(R.drawable.ic_launcher_background),
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = pokemon.name,
                    modifier = Modifier
                        .align(alignment = Alignment.BottomEnd)
                        .requiredWidth(width = 330.dp)
                        .requiredHeight(height = 119.dp)
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
                            contentDescription = type.toString(),
                            modifier = Modifier
                                .requiredHeight(height = 40.dp)
                                .requiredWidth(width = 40.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(widthDp = 440, heightDp = 160)
@Composable
fun PokemonCardPreview(){
    val pokemon = Pokemon(
        1,
        "bulbasaur",
        listOf("grass","poison"),
        "A strange seed was\nplanted on its\nback at birth.The plant sprouts\nand grows with\nthis POKéMON.",
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
        listOf("bulbasaur","ivysaur","venusaur")
        )
    PokedexTheme {
        PokemonCard(pokemon = pokemon, {}, modifier = Modifier)
    }
}
//#endregion