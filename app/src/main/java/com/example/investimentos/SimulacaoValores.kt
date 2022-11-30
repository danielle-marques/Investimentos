package com.example.investimentos

import com.example.investimentos.extensions.COMPRAR
import com.example.investimentos.model.MoedaModel

object SimulacaoValores {

    var toolbarSimulado: String? = null
    var operacaoSimulado: String? = null
    var saldoDisponivel = 1250.0

     private var valoresSimuladosHashmap = HashMap<String, Int>().apply {
        put("USD", 7)
        put("EUR", 5)
        put("GBP", 0)
        put("ARS", 8)
        put("CAD", 0)
        put("AUD", 1)
        put("JPY", 0)
        put("CNY", 5)
        put("BTC", 22)

    }
    fun buscaValoresHashmap(moeda: MoedaModel): Int {
        var valorSimulado = 0
        if(valoresSimuladosHashmap.containsKey(moeda.isoMoeda)) {
            valoresSimuladosHashmap.map {
                if (it.key == moeda.isoMoeda) {
                    valorSimulado = it.value
                }
            }
        }
        return valorSimulado


    }

    fun modificaValoresHashmap(
        isoMoeda: String,
        operacao: String,
        quantidade: Int
    ){
        valoresSimuladosHashmap.forEach {
            if(it.key == isoMoeda){
                var quantidadeSimulada = it.value
                if (operacao == COMPRAR){
                    quantidadeSimulada += quantidade
                }else{
                    quantidadeSimulada -= quantidade
                }
                valoresSimuladosHashmap[it.key] = quantidadeSimulada
            }
        }
    }


}