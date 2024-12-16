package com.dogs.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dogs.R
import com.dogs.compose.BreedDetailScreen
import com.dogs.compose.BreedsListScreen
import com.dogs.compose.ProgressBar
import com.dogs.compose.Screen
import com.dogs.domain.models.Breed
import com.dogs.viewmodels.BreedsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val breedsViewModel by viewModels<BreedsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScaffoldContent()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ScaffoldContent() {

        val navController: NavHostController = rememberNavController()
        var backEnabled by remember {
            mutableStateOf(false)
        }
        val snackbarHostState = remember { SnackbarHostState() }
        val breedsUiState by breedsViewModel.uiState.collectAsStateWithLifecycle()
        val showError = breedsUiState as? BreedsViewModel.BreedsUiState.OnError

        LaunchedEffect(showError) {
            showError?.let {
                val snackBarResult = snackbarHostState.showSnackbar(
                    actionLabel = showError.message,
                    message = getString(R.string.error_prefix),
                    withDismissAction = true
                )
                when (snackBarResult) {
                    SnackbarResult.Dismissed -> {
                        breedsViewModel.fetchBreeds()
                    }

                    else -> {
                        // do nothing
                    }
                }
            }
        }


        val icon = if (!backEnabled) {
            Icons.Filled.Home
        } else {
            Icons.AutoMirrored.Filled.ArrowBack
        }

        Scaffold(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            topBar = {
                TopAppBar(
                    title = { Text(getString(R.string.app_bar_title)) },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(icon, contentDescription = getString(R.string.app_bar_title))
                        }
                    }
                )
            },
            content = { innerPadding ->
                AppNavHost(
                    navController,
                    startDestination = Screen.HOME.route,
                    innerPadding = innerPadding,
                    backEnabled = {
                        backEnabled = it
                    },
                    breedsUiState = breedsUiState
                )
            }
        )
    }

    @Composable
    fun AppNavHost(
        navController: NavHostController,
        innerPadding: PaddingValues,
        startDestination: String = Screen.HOME.route,
        backEnabled: (flag: Boolean) -> Unit,
        breedsUiState: BreedsViewModel.BreedsUiState
    ) {
        var selectedBreed: Breed? by rememberSaveable {
            mutableStateOf(null)
        }

        var showLoading by remember {
            mutableStateOf(false)
        }

        AnimatedVisibility(
            modifier = Modifier
                .fillMaxSize(),
            visible = showLoading,
            enter = EnterTransition.None,
            exit = ExitTransition.None
        ) {
            ProgressBar(
                modifier = Modifier
                    .consumeWindowInsets(innerPadding)
                    .padding(innerPadding)
            )
        }


        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            // home screen
            composable(Screen.HOME.route) {
                backEnabled.invoke(false)
                when (breedsUiState) {
                    is BreedsViewModel.BreedsUiState.ShowBreeds -> {
                        showLoading = false

                        BreedsListScreen(modifier = Modifier
                            .fillMaxSize()
                            .consumeWindowInsets(innerPadding)
                            .padding(innerPadding),
                            breeds = breedsUiState.breedsList, onBreedClick = {
                                selectedBreed = breedsUiState.breedsList[it]
                                navController.navigate(Screen.BREED_DETAILS.route)
                            }, onBreedSearch = { findBreed ->
                                backEnabled.invoke(true)
                                breedsViewModel.findBreeds(findBreed)
                            })
                    }

                    is BreedsViewModel.BreedsUiState.Loading -> {
                        showLoading = true
                    }

                    is BreedsViewModel.BreedsUiState.OnError -> {
                        showLoading = false
                    }

                }
            }

            // details screen
            composable(Screen.BREED_DETAILS.route) {
                backEnabled.invoke(true)
                selectedBreed?.let {
                    BreedDetailScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .consumeWindowInsets(innerPadding)
                            .padding(innerPadding),
                        breed = it
                    )
                }
            }

        }
    }

}