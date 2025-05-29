package com.example.fugitive.data.dictionary

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response

// Step 1: Interface for API
interface DictionaryApi {
    @GET("api/v2/entries/en/{word}")
    suspend fun getMeaning(@Path("word") word: String): Response<List<DictionaryResponse>>
}

// Step 2: Retrofit Instance
object DictionaryRetrofitClient {
    val api: DictionaryApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }
}
