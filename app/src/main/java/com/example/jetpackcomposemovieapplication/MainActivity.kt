package com.example.jetpackcomposemovieapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposemovieapplication.ui.theme.JetpackComposeMovieApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MoviesListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeMovieApplicationTheme {
                val context = LocalContext.current
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(drawerContent = {
                    ModalDrawerSheet {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            NavigationDrawerItem(
                                label = { Text("Home") },
                                selected = false,
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = "Home",
                                        tint = Color.Black
                                    )
                                },
                                onClick = {
                                    Toast.makeText(context, "Home Menu Clicked", Toast.LENGTH_SHORT).show()
                                    scope.launch {
                                        drawerState.close()
                                    }
                                })
                            NavigationDrawerItem(
                                label = { Text("Favorite Movies") },
                                selected = false,
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = "Favorite Movies",
                                        tint = Color.Black
                                    )
                                },
                                onClick = {
                                    Toast.makeText(context, "Favorite Movies Menu Clicked", Toast.LENGTH_SHORT).show()
                                    scope.launch {
                                        drawerState.close()
                                    }
                                })
                            NavigationDrawerItem(
                                label = { Text("Settings") },
                                selected = false,
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Settings",
                                        tint = Color.Black
                                    )
                                },
                                onClick = {
                                    Toast.makeText(context, "Settings Menu Clicked", Toast.LENGTH_SHORT).show()
                                    scope.launch {
                                        drawerState.close()
                                    }
                                })
                            NavigationDrawerItem(
                                label = { Text("Help") },
                                selected = false,
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = "Help",
                                        tint = Color.Black
                                    )
                                },
                                onClick = {
                                    Toast.makeText(context, "Help Menu Clicked", Toast.LENGTH_SHORT).show()
                                    scope.launch {
                                        drawerState.close()
                                    }
                                })
                        }
                    }
                }, drawerState = drawerState) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            HomeScreenUI(viewModel, drawerState, scope)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreenUI(
    viewModel: MoviesListViewModel,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    val moviesList by viewModel.moviesList.collectAsState()
    val isLoading by viewModel.loading.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            FeaturedMovieHeader(drawerState, scope)
            Spacer(modifier = Modifier.height(16.dp))
            MoviesList(moviesList, viewModel::markFavMovie)
        }

        CircularProgressIndicator(
            modifier = if (isLoading) {
                Modifier
                    .size(48.dp)
                    .align(Alignment.Center)
            } else {
                Modifier
                    .height(0.dp)
                    .width(0.dp)
            }
        )
    }
}

@Composable
fun FeaturedMovieHeader(
    drawerState: DrawerState,
    scope: CoroutineScope
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {

        FeaturedMovieHeaderImage()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HamburgerMenuIcon {
                scope.launch {
                    if (drawerState.isOpen) {
                        drawerState.close()
                    } else {
                        drawerState.open()
                    }
                }
            }
            SearchMovie { }
        }
    }
}

@Composable
fun FeaturedMovieHeaderImage() {
    Image(
        painter = painterResource(R.drawable.ic_launcher_foreground),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun HamburgerMenuIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = Color.Black
        )
    }
}

@Composable
fun SearchMovie(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Movies",
            tint = Color.Black
        )
    }
}

@Composable
fun MoviesList(
    moviesList: List<MoviesListModel>, onFavToggle: (Int) -> Unit
) {
    println("movies list: $moviesList")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(moviesList) { movie ->
            MovieItem(movie) {
                onFavToggle(it)
            }
        }
    }
}

@Composable
fun MovieItem(movie: MoviesListModel, onFavToggle: (Int) -> Unit) {

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = movie.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .aspectRatio(2f / 3f)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = movie.header,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    FavMovieToggleIcon(movie, onFavToggle)
                }

                Text(
                    text = movie.rating,
                    color = Color.Gray,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = movie.description,
                    color = Color.Gray,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(4.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

@Composable
fun FavMovieToggleIcon(movie: MoviesListModel, onFavToggle: (Int) -> Unit) {
    IconButton(onClick = { onFavToggle(movie.id) }) {
        Icon(
            imageVector = if (movie.isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = if (movie.isFav) "Favorite" else "Not Favorite"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FeaturedMovieHeaderPreview() {
    JetpackComposeMovieApplicationTheme {
        FeaturedMovieHeader(rememberDrawerState(DrawerValue.Closed), rememberCoroutineScope())
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesListPreview() {
    JetpackComposeMovieApplicationTheme {
        MoviesList(moviesList) {}
    }
}




