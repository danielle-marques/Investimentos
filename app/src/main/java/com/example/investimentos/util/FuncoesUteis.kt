package com.example.investimentos.util

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.investimentos.SingletonSimulacaoValores
import com.example.investimentos.SingletonSimulacaoValores.ars
import com.example.investimentos.SingletonSimulacaoValores.aud
import com.example.investimentos.SingletonSimulacaoValores.btc
import com.example.investimentos.SingletonSimulacaoValores.cad
import com.example.investimentos.SingletonSimulacaoValores.cny
import com.example.investimentos.SingletonSimulacaoValores.eur
import com.example.investimentos.SingletonSimulacaoValores.gbp
import com.example.investimentos.SingletonSimulacaoValores.jpy
import com.example.investimentos.SingletonSimulacaoValores.usd
import com.example.investimentos.model.MoedaModel
import java.text.DecimalFormat

object FuncoesUteis {


    @SuppressLint("SetTextI18n")
    fun incicializarPorcentagemCor(
        itemPorcentagem: TextView,
        moedaModel: MoedaModel
    ) {
        if (moedaModel.porcentagem!! < 0) {

            itemPorcentagem.setTextColor(Color.RED)
        } else if (moedaModel.porcentagem!! > 0) {
            itemPorcentagem.setTextColor(Color.GREEN)
        } else {
            itemPorcentagem.setTextColor(Color.WHITE)
        }
        val formatador = DecimalFormat("#.##")
        itemPorcentagem.text = formatador.format(moedaModel.porcentagem).toString() + "%"
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

    fun calculoOperacao(moeda: MoedaModel) {

        when (moeda.isoMoeda) {
            "USD" -> usd = moeda.saldoMoedaEmCaixa
            "EUR" -> eur = moeda.saldoMoedaEmCaixa
            "GBP" -> gbp = moeda.saldoMoedaEmCaixa
            "ARS" -> ars = moeda.saldoMoedaEmCaixa
            "CAD" -> cad = moeda.saldoMoedaEmCaixa
            "AUD" -> aud = moeda.saldoMoedaEmCaixa
            "JPY" -> jpy = moeda.saldoMoedaEmCaixa
            "CNY" -> cny = moeda.saldoMoedaEmCaixa
            "BTC" -> btc = moeda.saldoMoedaEmCaixa
        }
    }

    fun validaQuantidadeNaVenda(quantidade: Int, moedaModel: MoedaModel): Boolean {
        if (quantidade <= moedaModel.saldoMoedaEmCaixa && quantidade != 0) {
            return true
        }
        return false
    }

    fun validaQuantidadeNaCompra(quantidade: Int, moedaModel: MoedaModel): Boolean {
        if (quantidade * moedaModel.compra!! <= SingletonSimulacaoValores.saldoDisponivel && quantidade != 0) {
            return true
        }
        return false
    }

    fun buscaValorSimuladoParaModel(moeda: MoedaModel) {
        when (moeda.isoMoeda) {
            "USD" -> moeda.saldoMoedaEmCaixa = usd
            "EUR" -> moeda.saldoMoedaEmCaixa = eur
            "GBP" -> moeda.saldoMoedaEmCaixa = gbp
            "ARS" -> moeda.saldoMoedaEmCaixa = ars
            "CAD" -> moeda.saldoMoedaEmCaixa = cad
            "AUD" -> moeda.saldoMoedaEmCaixa = aud
            "JPY" -> moeda.saldoMoedaEmCaixa = jpy
            "CNY" -> moeda.saldoMoedaEmCaixa = cny
            "BTC" -> moeda.saldoMoedaEmCaixa = btc
        }
    }
    @BindingAdapter("increaseTouch")
    fun increaseTouch(view: View, value: Float) {
        val parent = view.parent
        (parent as View).post {
            val rect = Rect()
            view.getHitRect(rect)
            val intValue = value.toInt()
            rect.top -= intValue // increase top hit area
            rect.left -= intValue // increase left hit area
            rect.bottom += intValue // increase bottom hit area
            rect.right += intValue // increase right hit area
            parent.touchDelegate = TouchDelegate(rect, view)
        }
    }

}