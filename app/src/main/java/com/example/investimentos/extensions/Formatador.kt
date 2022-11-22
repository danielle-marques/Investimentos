package com.example.investimentos.extensions

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*



fun formataPorcentagem(valor: Double?): String {

    val formatador = NumberFormat.getNumberInstance(Locale("pt", "BR"))
    formatador.maximumFractionDigits = 2
    formatador.minimumFractionDigits = 2
    return "${formatador.format(valor)} %"

}


fun formataMoeda(valor: Double?): String {
    return DecimalFormat.getCurrencyInstance(Locale("pt", "BR")).format(valor)
}

