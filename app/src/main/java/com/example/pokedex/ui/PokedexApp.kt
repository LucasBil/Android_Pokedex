package com.example.pokedex.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedex.R
import com.example.pokedex.model.Pokemon
import com.example.pokedex.ui.screens.DetailScreen
import com.example.pokedex.ui.screens.HomeScreen
import com.example.pokedex.ui.screens.PokedexUiState
import com.example.pokedex.ui.screens.PokemonCard
import com.example.pokedex.ui.screens.PokemonViewModel
import com.example.pokedex.ui.theme.PokedexTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.lang.IllegalStateException
import java.util.Locale

object Route {
    const val HOME = "home"
    const val DETAIL = "detail"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexApp(){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val navController = rememberNavController()
    val scrollState = rememberLazyGridState()
    val pokemonViewModel: PokemonViewModel = viewModel(factory = PokemonViewModel.Factory)
    val uiState = pokemonViewModel.pokedexUiState

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopAppBar(scrollBehavior = scrollBehavior, { navController.popBackStack()}) },
        bottomBar = { BottomAppBar(Modifier.fillMaxWidth(), navController) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(navController = navController, startDestination = Route.HOME) {
                composable(Route.HOME) {
                    when(uiState){
                        is PokedexUiState.Success -> {
                            HomeScreen(
                                pokemons = uiState.pokemons,
                                retryAction = {pokemonViewModel.getAll()},
                                onPokemonCardClick = { pokemon ->
                                    navController.navigate("${Route.DETAIL}/${pokemon.id}")
                                },
                                scrollState = scrollState
                            )
                        }
                        is PokedexUiState.Error -> {
                            ErrorScreen(modifier = Modifier, {pokemonViewModel.getAll()})
                        }
                        is PokedexUiState.Loading -> {
                            LoadingScreen(modifier = Modifier)
                        }
                    }
                }
                composable("${Route.DETAIL}/{pokemonId}",
                    arguments = listOf(navArgument("pokemonId") { type = NavType.IntType })
                ) { backStackEntry ->
                    when (uiState){
                        is PokedexUiState.Success -> {
                            val selectedPokemonId = backStackEntry.arguments?.getInt("pokemonId") ?: throw IllegalStateException("Id null")
                            val pokemon : Pokemon? = uiState.pokemons.find { it.id == selectedPokemonId }
                            if (pokemon == null)
                                ErrorScreen(modifier = Modifier, {pokemonViewModel.getAll()})
                            val family = uiState.pokemons.filter {
                                it.name.lowercase(Locale.ROOT) in pokemon!!.family.map(String::toLowerCase)
                            }
                            DetailScreen(
                                pokemon = pokemon!!,
                                family = family,
                                onSwitchPokemon = { pokemon ->
                                    navController.navigate("${Route.DETAIL}/${pokemon.id}")
                                }
                            )
                        }
                        is PokedexUiState.Error -> {
                            ErrorScreen(modifier = Modifier, {pokemonViewModel.getAll()})
                        }
                        is PokedexUiState.Loading -> {
                            LoadingScreen(modifier = Modifier)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    goBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    PokedexTheme(darkTheme = isSystemInDarkTheme()) {
        val colorScheme = MaterialTheme.colorScheme
        SmallTopAppBar(
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorScheme.primary
            ),
            title = {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                ){
                    Row (
                        modifier= modifier
                            .align(Alignment.CenterStart),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Button(
                            onClick = goBack,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            modifier = modifier
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.arrow),
                                contentDescription = "arrow",
                                modifier = modifier,
                                colorFilter = ColorFilter.tint(colorScheme.onPrimary)
                            )
                        }
                        Spacer(modifier = modifier.requiredWidth(15.dp))
                        Text(
                            text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.headlineSmall,
                            color = colorScheme.onPrimary,
                            modifier = modifier
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.icon_pokeball),
                        contentDescription = "Icon Pokeball",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .offset(
                                x = (-10).dp
                            )
                            .rotate(degrees = -25f)
                            .requiredWidth(width = 49.dp)
                            .requiredHeight(height = 49.dp)
                    )
                }
            },
            modifier = modifier
                .clip(shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
        )
    }
}

@Composable
fun BottomAppBar(
    modifier: Modifier,
    navController: NavController
){
    PokedexTheme(darkTheme = isSystemInDarkTheme()) {
        val colorScheme = MaterialTheme.colorScheme
        Box(
            modifier = modifier
                .requiredHeight(height = 85.dp)
                .clip(shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                .background(color = colorScheme.primary),
            contentAlignment = Alignment.Center
        ){
            Button(
                modifier= Modifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                onClick = { navController.navigate("${Route.HOME}") }
            ) {
                Image(painter = painterResource(
                    id = R.drawable.home_button),
                    contentDescription = "Home",
                    colorFilter = ColorFilter.tint(colorScheme.onPrimary),
                    modifier = Modifier
                        .requiredHeight(height = 32.dp)
                        .requiredWidth(width = 32.dp)
                )
            }
        }
    }
}

@Preview(widthDp = 440, heightDp = 160)
@Composable
fun BottomAppBarPreview(){
    val navController = rememberNavController()
    PokedexTheme {
        BottomAppBar(Modifier, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(widthDp = 440, heightDp = 160)
@Composable
fun TopAppBarPreview(){
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    PokedexTheme {
        TopAppBar(scrollBehavior, {  }, Modifier)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier){
    Column (
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_pokeball),
            contentDescription = "Pokeball"
        )
        Spacer(modifier = Modifier.height(height = 10.dp))
        Text(text = "Loading ...")
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier,
    refreshPokemons: () -> Unit
){
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    PokedexTheme {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = refreshPokemons
        ) {
            Column (
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pictur_error),
                    contentDescription = "Error 404")
                Text(text = "An error as been detected")
            }
        }
    }
}