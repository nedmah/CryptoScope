package com.example.cryptoscope

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.accounts.presentation.AccountsScreen
import com.example.common_ui.R
import com.example.common_ui.composable.CryptoBasicScaffold
import com.example.common_ui.composable.bottom_nav.BottomBarScreens
import com.example.common_ui.composable.bottom_nav.CryptoBottomBar
import com.example.common_ui.theme.CryptoScopeTheme
import com.example.core.data.settings.SettingsConstants
import com.example.core.util.extensions.navigate
import com.example.crypto_info.presentation.crypto_info.CryptoInfoScreen
import com.example.crypto_info.presentation.crypto_info.CryptoInfoViewModel
import com.example.core.domain.model.CryptoListingsModel
import com.example.core.domain.settings.SettingsDataStore
import com.example.core.util.LocaleHelper
import com.example.crypto_info.presentation.comparison.CryptoComparisonScreen
import com.example.cryptolisting.presentation.CryptoListingsScreen
import com.example.cryptoscope.di.appComponent
import com.example.cryptoscope.di.viewmodel.MultiViewModelFactory
import com.example.cryptoscope.navigation.Routes
import com.example.currency.presentation.CurrencyRatesScreen
import com.example.language.LanguageScreen
import com.example.news.presentation.CryptoNewsScreen
import com.example.settings.presentation.SettingsScreen
import com.example.wallet.presentation.CryptoWalletScreen
import com.example.wallet.presentation.my_coins.MyCoinsScreen
import com.example.wallet.presentation.wallet_history.WalletHistoryScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)


        enableEdgeToEdge()
        setContent {

            CoroutineScope(Dispatchers.IO).launch {
                LocaleHelper.initializeLocale(viewModel.settingsDataStore, this@MainActivity)
            }
            val isDarkTheme by viewModel.themeFlow.collectAsState(initial = true)

            if (isDarkTheme != null) {
                CryptoScopeTheme(darkTheme = isDarkTheme ?: true, dynamicColor = true) {
                    Surface {
                        CryptoNavGraph(
                            navController = rememberNavController(),
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CryptoNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel
) {

    val getViewModelFactory: () -> MultiViewModelFactory = remember {
        { viewModel.factory }
    }

    val bottomNavItems = listOf(
        BottomBarScreens(
            0,
            Routes.CryptoNewsScreen.name,
            stringResource(id = R.string.news),
            R.drawable.ic_news_24
        ),
        BottomBarScreens(
            1,
            Routes.CryptoListingsScreen.name,
            stringResource(id = R.string.market),
            R.drawable.ic_market_24
        ),
        BottomBarScreens(
            2,
            Routes.CryptoWalletScreen.name,
            stringResource(id = R.string.wallet),
            R.drawable.ic_wallet_24
        ),
        BottomBarScreens(
            3,
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
                currentIndex.intValue = it.id
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
                val vm: CryptoInfoViewModel = viewModel(
                    factory = viewModel.cryptoInfoViewModelFactory.create(cryptoData)
                )
                CryptoInfoScreen(vm) {
                    navController.navigateUp()
                }
            }

            composable(Routes.CryptoWalletScreen.name) {
                CryptoWalletScreen(
                    getViewModelFactory,
                    navigateWithBundle = { cryptoModelBundle ->
                        navController.navigate(
                            route = Routes.CryptoInfoScreen.name,
                            args = cryptoModelBundle,
                            navOptions = NavOptions.Builder()
                                .setLaunchSingleTop(true).build()
                        )
                    },
                    navigateToMyCoins = {
                        navController.navigate(Routes.CryptoMyCoinsScreen.name)
                    },
                    navigateToWalletHistory = {
                        navController.navigate(Routes.CryptoWalletHistoryScreen.name)
                    }
                )
            }

            composable(Routes.CryptoMyCoinsScreen.name) {
                MyCoinsScreen(
                    getViewModelFactory,
                    { navController.navigateUp() })
            }

            composable(Routes.CryptoWalletHistoryScreen.name) {
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
                    getViewModelFactory,
                    navigate = { route ->
                        route?.let { it1 -> navController.navigate(it1) }
                    }
                )
            }

            composable(com.example.settings.navigation.Routes.CryptoAccountsScreen.name) {
                AccountsScreen(
                    getViewModelFactory = getViewModelFactory,
                    onBack = { navController.navigateUp() })
            }

            composable(com.example.settings.navigation.Routes.CryptoComparisonScreen.name) {
                CryptoComparisonScreen(
                    getViewModelFactory,
                    onBack = { navController.navigateUp() },
                )
            }

            composable(com.example.settings.navigation.Routes.CryptoLanguageScreen.name) {
                LanguageScreen(
                    getViewModelFactory,
                    onBack = { navController.navigateUp() },
                    onLanguageSelected = {}
                )
            }

            composable(com.example.settings.navigation.Routes.CryptoCurrencyScreen.name) {
                CurrencyRatesScreen(
                    getViewModelFactory,
                    onBack = { navController.navigateUp() }
                )
            }

            composable(com.example.settings.navigation.Routes.CryptoAboutScreen.name) {
            }
        }

    }

}

