package com.example.investimentos.api

import com.example.investimentos.extensions.ID_LOGIN_API
import retrofit2.http.GET

interface MoedasApi {

   @GET(ID_LOGIN_API)
   suspend fun buscaMoedas() : Endpoint

  }