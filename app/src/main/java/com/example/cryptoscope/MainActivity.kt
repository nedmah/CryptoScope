package com.example.cryptoscope

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.common_ui.theme.CryptoScopeTheme
import com.example.core.util.extensions.navigate
import com.example.crypto_info.presentation.CryptoInfoScreen
import com.example.crypto_info.presentation.CryptoInfoViewModel
import com.example.core.domain.model.CryptoListingsModel
import com.example.cryptolisting.presentation.CryptoListingsScreen
import com.example.cryptoscope.di.appComponent
import com.example.cryptoscope.di.viewmodel.MultiViewModelFactory
import javax.inject.Inject


class MainActivity : ComponentActivity() {

    @Inject
    lateinit var factory: MultiViewModelFactory

    @Inject
    lateinit var cryptoInfoViewModelFactory: CryptoInfoViewModel.CryptoInfoViewModelFactory.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)

        enableEdgeToEdge()
        setContent {
            CryptoScopeTheme {
                Surface {

                    val navController = rememberNavController()
                    val getViewModelFactory: () -> MultiViewModelFactory = remember {
                        { factory }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = "CryptoListings"
                    ) {
                        composable(route = "CryptoListings") {
                            CryptoListingsScreen(
                                navigate = { cryptoModelBundle ->
                                    navController.navigate(
                                        route = "CryptoInfoScreen",
                                        args = cryptoModelBundle
                                    )
                                },
                                getViewModelFactory = getViewModelFactory
                            )
                        }
                        composable(route = "CryptoInfoScreen") {

                            val cryptoData =
                                navController.currentBackStackEntry?.arguments?.getParcelable<CryptoListingsModel>(
                                    "cryptoInfo"
                                )

                            if (cryptoData != null) {
                                val vm : CryptoInfoViewModel = viewModel(factory = cryptoInfoViewModelFactory.create(cryptoData))
                                CryptoInfoScreen(viewModel = vm) {
                                    navController.navigateUp()
                                }
                            } else navController.navigateUp()
                        }
                    }
                }
            }
        }
    }
}




