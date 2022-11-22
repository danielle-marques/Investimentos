package com.example.investimentos.util

import android.graphics.Color
import android.widget.TextView
import com.example.investimentos.model.MoedaModel

object FuncoesUteis {


    fun alteraCorDaPorcentagem(
        itemPorcentagem: TextView,
        moedaModel: MoedaModel
    ) {
        val variacaoPorcentagem = moedaModel.porcentagem
        val cor: String
        if (variacaoPorcentagem != null) {
            cor = when {
                variacaoPorcentagem < 0 -> "#D0021B"
                variacaoPorcentagem > 0 -> "#7ED321"
                else -> "#ffffff"
            }
            itemPorcentagem.setTextColor(Color.parseColor(cor))
        }

    }


    fun mapeiaMoedaParaIso(moedas: List<MoedaModel?>): List<MoedaModel?> {
        return moedas.map {
            it?.apply {
                it.isoMoeda =
                    when (it.moeda) {
                        "Dollar" -> "USD"
                        "Euro" -> "EUR"
                        "Pound Sterling" -> "GBP"
                        "Argentine Peso" -> "ARS"
                        "Canadian Dollar" -> "CAD"
                        "Australian Dollar" -> "AUD"
                        "Japanese Yen" -> "JPY"
                        "Renminbi" -> "CNY"
                        "Bitcoin" -> "BTC"
                        else -> ""
                    }
            }
        }
    }








}