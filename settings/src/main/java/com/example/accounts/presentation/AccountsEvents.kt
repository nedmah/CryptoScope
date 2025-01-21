package com.example.accounts.presentation

import com.example.accounts.domain.model.AccountsModel

sealed class AccountsEvents {

    data class AddAccount(val name: String?, val address : String) : AccountsEvents()
    data class DeleteAccount(val model: AccountsModel) : AccountsEvents()
    data object OnAddPush : AccountsEvents()
    data class OnAccountSelect(val blockchain : String) : AccountsEvents()

}