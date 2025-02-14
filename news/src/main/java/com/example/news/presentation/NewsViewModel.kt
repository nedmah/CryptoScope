package com.example.news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.example.core.data.db.entities.CryptoNewsEntity
import com.example.core.domain.model.CryptoListingsModel
import com.example.core.domain.repository.FavouritesRepository
import com.example.news.data.remote.CryptoNewsPager
import com.example.news.data.toCryptoNewsModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    pager: CryptoNewsPager
) : ViewModel() {

    val pageFlow = pager.pager.flow.map { it.map { entity -> entity.toCryptoNewsModel() } }.cachedIn(viewModelScope)

}