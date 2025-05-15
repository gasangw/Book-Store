package io.thomasgasangwa.bookstore.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import io.thomasgasangwa.bookstore.common.Constants.BASE_URL

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()