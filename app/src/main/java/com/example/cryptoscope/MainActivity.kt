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
import com.example.cryptoscope.navigation.Routes
import com.example.wallet.presentation.CryptoWalletScreen
import com.example.wallet.presentation.wallet_history.WalletHistoryScreen
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
                        startDestination = Routes.CryptoListingsScreen.name
                    ) {
                        composable(Routes.CryptoListingsScreen.name) {
                            CryptoListingsScreen(
                                navigate = { cryptoModelBundle ->
                                    navController.navigate(
                                        route = Routes.CryptoInfoScreen.name,
                                        args = cryptoModelBundle
                                    )
                                },
                                getViewModelFactory = getViewModelFactory
                            )
                        }
                        composable(Routes.CryptoInfoScreen.name) {

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

                        composable(Routes.CryptoWalletScreen.name) {
                            CryptoWalletScreen(getViewModelFactory = getViewModelFactory)
                        }

                        composable(Routes.CryptoWalletBalanceScreen.name) {
                            WalletHistoryScreen(getViewModelFactory = getViewModelFactory){
                                navController.navigateUp()
                            }
                        }
                    }
                }
            }
        }
    }
}




