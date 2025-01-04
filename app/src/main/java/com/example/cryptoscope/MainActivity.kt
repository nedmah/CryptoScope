package com.example.cryptoscope

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.common_ui.R
import com.example.common_ui.composable.CryptoBasicScaffold
import com.example.common_ui.composable.bottom_nav.BottomBarScreens
import com.example.common_ui.composable.bottom_nav.CryptoBottomBar
import com.example.common_ui.theme.CryptoScopeTheme
import com.example.core.util.extensions.navigate
import com.example.crypto_info.presentation.crypto_info.CryptoInfoScreen
import com.example.crypto_info.presentation.crypto_info.CryptoInfoViewModel
import com.example.core.domain.model.CryptoListingsModel
import com.example.crypto_info.presentation.comparison.CryptoComparisonScreen
import com.example.cryptolisting.presentation.CryptoListingsScreen
import com.example.cryptoscope.di.appComponent
import com.example.cryptoscope.di.viewmodel.MultiViewModelFactory
import com.example.cryptoscope.navigation.Routes
import com.example.news.presentation.CryptoNewsScreen
import com.example.settings.presentation.SettingsScreen
import com.example.wallet.presentation.CryptoWalletScreen
import com.example.wallet.presentation.wallet_history.WalletHistoryScreen
import javax.inject.Inject


class MainActivity : ComponentActivity() {

    @Inject
    lateinit var factory: MultiViewModelFactory

    @Inject
    lateinit var cryptoInfoViewModelFactory: CryptoInfoViewModel.CryptoInfoViewModelFactory.Factory

    @OptIn(ExperimentalMaterial3Api::class)
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

                    val bottomNavItems = listOf(
                        BottomBarScreens(
                            Routes.CryptoNewsScreen.name,
                            stringResource(id = R.string.news),
                            R.drawable.ic_news_24
                        ),
                        BottomBarScreens(
                            Routes.CryptoListingsScreen.name,
                            stringResource(id = R.string.market),
                            R.drawable.ic_market_24
                        ),
                        BottomBarScreens(
                            Routes.CryptoWalletScreen.name,
                            stringResource(id = R.string.wallet),
                            R.drawable.ic_wallet_24
                        ),
                        BottomBarScreens(
                            Routes.CryptoSettingsScreen.name,
                            stringResource(id = R.string.settings),
                            R.drawable.ic_settings_24
                        )
                    )
                    val currentIndex = rememberSaveable {
                        mutableIntStateOf(1)
                    }

                    CryptoBasicScaffold(
                        bottomBar = {
                            CryptoBottomBar(
                                navController = navController,
                                bottomNavigationItems = bottomNavItems,
                                initialIndex = currentIndex
                            ) {
                                Log.i("SELECTED_ITEM", "onCreate: Selected Item ${it.name}")
                            }
                        }
                    ) {


                        NavHost(
                            navController = navController,
                            startDestination = Routes.CryptoListingsScreen.name
                        ) {
                            composable(Routes.CryptoListingsScreen.name) {
                                CryptoListingsScreen(
                                    getViewModelFactory,
                                    navigate = { cryptoModelBundle ->
                                        navController.navigate(
                                            route = Routes.CryptoInfoScreen.name,
                                            args = cryptoModelBundle,
                                            navOptions = NavOptions.Builder()
                                                .setLaunchSingleTop(true).build()
                                        )
                                    }
                                )
                            }
                            composable(Routes.CryptoInfoScreen.name) {
                                val cryptoData =
                                    navController.currentBackStackEntry?.arguments?.getParcelable<CryptoListingsModel>(
                                        "cryptoInfo"
                                    )
                                if (cryptoData != null) {
                                    val vm: CryptoInfoViewModel = viewModel(
                                        factory = cryptoInfoViewModelFactory.create(cryptoData)
                                    )
                                    CryptoInfoScreen(vm) {
                                        navController.navigateUp()
                                    }
                                } else navController.navigateUp()
                            }

                            composable(Routes.CryptoWalletScreen.name) {
                                CryptoWalletScreen(
                                    getViewModelFactory
                                )
                            }

                            composable(Routes.CryptoWalletBalanceScreen.name) {
                                WalletHistoryScreen(
                                    getViewModelFactory,
                                    { navController.navigateUp() })
                            }

                            composable(Routes.CryptoNewsScreen.name) {
                                CryptoNewsScreen(
                                    getViewModelFactory
                                )
                            }
                            composable(Routes.CryptoSettingsScreen.name) {
                                SettingsScreen(
                                    action = { route ->
                                        route?.let { navController.navigate(it) }
                                    }
                                )
                            }

                            composable(com.example.settings.navigation.Routes.CryptoAccountsScreen.name) {}

                            composable(com.example.settings.navigation.Routes.CryptoComparisonScreen.name) {
                                CryptoComparisonScreen(
                                    onBack = { navController.navigateUp() },
                                    onCompare = { /*TODO*/ })
                            }

                            composable(com.example.settings.navigation.Routes.CryptoCurrencyScreen.name) {}

                            composable(com.example.settings.navigation.Routes.CryptoAboutScreen.name) {}
                        }
                    }
                }
            }
        }
    }
}




