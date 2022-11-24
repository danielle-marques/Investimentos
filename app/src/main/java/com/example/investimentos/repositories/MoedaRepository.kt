package com.example.investimentos.repositories

import com.example.investimentos.api.Endpoint
import com.example.investimentos.util.RetrofitUtil

class MoedaRepository {
    private val moedaApi = RetrofitUtil().initApiServices()

    suspend fun buscaMoedaRepository(): Endpoint {
        return moedaApi.buscaMoedas()
    }

}