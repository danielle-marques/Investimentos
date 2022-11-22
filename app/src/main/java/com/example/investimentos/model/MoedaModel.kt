package com.example.investimentos.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class MoedaModel (

    var isoMoeda: String = "",
    @SerializedName("name")
    var moeda: String? = null,
    @SerializedName("buy")
    var compra: Double? = 00.0,
    @SerializedName("sell")
    var venda: Double? = 00.0,
    @SerializedName("variation")
    var porcentagem: Double? = null
    ) : Serializable