package com.example.cryptoscope.navigation


sealed interface Routes {

    data object CryptoListingsScreen : Routes{
        val name : String = "CryptoListingsScreen"
    }

    data object CryptoInfoScreen : Routes{
        val name : String = "CryptoInfoScreen"
    }

    data object CryptoWalletScreen : Routes{
        val name : String = "CryptoWalletScreen"
    }

    data object CryptoWalletBalanceScreen : Routes{
        val name : String = "CryptoWalletBalanceScreen"
    }
}
