package com.example.cryptoscope

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.common_ui.composable.CryptoScrollableScaffold
import com.example.common_ui.theme.CryptoScopeTheme
import com.example.core.util.extensions.navigate
import com.example.crypto_info.presentation.CryptoInfoScreen
import com.example.crypto_info.presentation.CryptoInfoViewModel
import com.example.cryptolisting.domain.model.CryptoListingsModel
import com.example.cryptolisting.presentation.CryptoListingsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoScopeTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "CryptoListings"
                    ) {
                        composable(route = "CryptoListings") {
                            CryptoListingsScreen(navigate = { cryptoModelBundle ->
                                navController.navigate(
                                    route = "CryptoInfoScreen",
                                    args = cryptoModelBundle
                                )
                            })
                        }

                        composable(route = "CryptoInfoScreen") {
                            val cryptoData =
                                navController.currentBackStackEntry?.arguments?.getParcelable<CryptoListingsModel>(
                                    "cryptoInfo"
                                )
                            if (cryptoData != null){
//                                val cryptoInfoViewModel : CryptoInfoViewModel = hiltViewModel()
                                CryptoInfoScreen(){
                                    navController.navigateUp()

//                                    navController.navigate("CryptoListings") {
//                                        popUpTo("CryptoListings") {
//                                            inclusive = true
//                                        }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}




