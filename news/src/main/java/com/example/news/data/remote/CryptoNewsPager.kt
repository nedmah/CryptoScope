package com.example.news.data.remote

import androidx.paging.Pager
import com.example.core.data.db.entities.CryptoNewsEntity
import javax.inject.Inject

class CryptoNewsPager@Inject constructor(
    val pager: Pager<Int, CryptoNewsEntity>
)