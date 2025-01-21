package com.example.accounts.data

import com.example.accounts.data.network.BlockchainsDto
import com.example.accounts.domain.model.AccountsModel
import com.example.accounts.domain.model.BlockchainModel
import com.example.core.data.db.entities.AccountsEntity
import com.example.core.data.db.entities.BlockchainsEntity

fun BlockchainsEntity.toBlockchainModel(): BlockchainModel {
    return BlockchainModel(
        connectionId = connectionId,
        name = name,
        icon = icon,
        chain = chain
    )
}

fun BlockchainsDto.toBlockchainModel(): BlockchainModel {
    return BlockchainModel(
        connectionId = connectionId,
        name = name,
        icon = icon,
        chain = chain
    )
}

fun BlockchainsDto.toBlockchainEntity(): BlockchainsEntity {
    return BlockchainsEntity(
        connectionId = connectionId,
        name = name,
        icon = icon,
        chain = chain
    )
}

fun AccountsEntity.toAccountsModel(): AccountsModel {
    return AccountsModel(
        address = address,
        blockChain = blockChain,
        imageUrl = imageUrl
    )
}

fun AccountsModel.toAccountsEntity(): AccountsEntity {
    return AccountsEntity(
        address = address,
        blockChain = blockChain,
        imageUrl = imageUrl
    )
}