package com.dogs.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dogs.compose.BreedDetailScreen
import com.dogs.compose.BreedsListScreen
import com.dogs.compose.Screen
import com.dogs.domain.models.Breed
import com.dogs.viewmodels.BreedsViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val breedsViewModel by viewModels<BreedsViewModel>()

    private var breedsList: List<Breed> by mutableStateOf(emptyList())

    private var selectedBreedIndex: Int by mutableIntStateOf(-1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScaffoldContent()
        }
        observeEvents()
        initialiseView()
    }


    private fun initialiseView() {
        breedsViewModel.fetchBreeds()
    }

    private fun observeEvents() {
        breedsViewModel.breedsViewState.observe(this) {
            Timber.d("Search observeEvents() >>>> $it")
            when (it) {
                is BreedsViewModel.BreedsViewState.OnError -> {
                }
                is BreedsViewModel.BreedsViewState.ShowBreeds -> {
                    breedsList = it.breedsList
                }

                else -> {
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ScaffoldContent() {

        val navController: NavHostController = rememberNavController()
        var backEnabled by remember {
            mutableStateOf(false)
        }
        val icon = if (!backEnabled) {
            Icons.Filled.Home
        } else {
            Icons.AutoMirrored.Filled.ArrowBack
        }

        Scaffold(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            topBar = {
                TopAppBar(
                    title = { Text("Dogs Breeds") },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(icon, contentDescription = "Dogs Breeds Icon")
                        }
                    }
                )
            },
            content = { innerPadding ->
                AppNavHost(
                    navController,
                    startDestination = Screen.HOME.route,
                    innerPadding = innerPadding
                ) {
                    backEnabled = it
                }
            }
        )
    }

    @Composable
    fun AppNavHost(
        navController: NavHostController,
        innerPadding: PaddingValues,
        startDestination: String = Screen.HOME.route,
        backEnabled: (flag: Boolean) -> Unit
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            // home screen
            composable(Screen.HOME.route) {
                backEnabled.invoke(false)
                BreedsListScreen(modifier = Modifier
                    .fillMaxSize()
                    .consumeWindowInsets(innerPadding)
                    .padding(innerPadding),
                    breeds = breedsList, onBreedClick = {
                        selectedBreedIndex = it
                        navController.navigate(Screen.BREED_DETAILS.route)
                    })
            }

            // details screen
            composable(Screen.BREED_DETAILS.route) {
                backEnabled.invoke(true)
                BreedDetailScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .consumeWindowInsets(innerPadding)
                        .padding(innerPadding),
                    breed = breedsList[selectedBreedIndex]
                )
            }

        }
    }

}