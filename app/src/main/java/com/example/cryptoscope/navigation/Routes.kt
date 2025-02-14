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

    data object CryptoMyCoinsScreen : Routes{
        val name : String = "CryptoMyCoinsScreen"
    }

    data object CryptoWalletHistoryScreen : Routes{
        val name : String = "CryptoWalletHistoryScreen"
    }

    data object CryptoNewsScreen : Routes{
        val name : String = "CryptoNewsScreen"
    }

    data object CryptoSettingsScreen : Routes{
        val name : String = "CryptoSettingsScreen"
    }

}
