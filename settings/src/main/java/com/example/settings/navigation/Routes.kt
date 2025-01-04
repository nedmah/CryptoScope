package com.example.settings.navigation


sealed interface Routes {

    data object CryptoComparisonScreen : Routes{
        val name : String = "CryptoComparisonScreen"
    }

    data object CryptoAccountsScreen : Routes{
        val name : String = "CryptoAccountsScreen"
    }

    data object CryptoCurrencyScreen : Routes{
        val name : String = "CryptoCurrencyScreen"
    }

    data object CryptoAboutScreen : Routes{
        val name : String = "CryptoAboutScreen"
    }
}
