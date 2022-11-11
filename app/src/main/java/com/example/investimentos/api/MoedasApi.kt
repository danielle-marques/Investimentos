package com.example.investimentos.api

import retrofit2.http.GET

interface MoedasApi {

   @GET("finance?fields=only_results,currencies&key=721c629f")
   suspend fun buscaMoedas() : Endpoint

  }