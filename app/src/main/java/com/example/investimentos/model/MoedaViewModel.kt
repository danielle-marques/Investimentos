package com.example.investimentos.model

import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.MutableLiveData
import com.example.investimentos.R
import com.example.investimentos.repositories.MoedaRepository
import com.example.investimentos.util.FuncoesUteis.mapeiaMoedaParaIso
import kotlinx.coroutines.launch

class MoedaViewModel(private val moedaRepository: MoedaRepository) : BaseViewModel() {


    val listaDeMoedas = MutableLiveData<List<MoedaModel?>>()
    val mensagemDeErro = MutableLiveData<String>()

    fun atualizadorDeMoedas() {
        launch {
            try {

                val call = moedaRepository.buscaMoedaRepository()
                val listaNovaMoedas = mapeiaMoedaParaIso(
                    listOfNotNull(
                        call.currencies.USD,
                        call.currencies.EUR,
                        call.currencies.ARS,
                        call.currencies.CAD,
                        call.currencies.GBP,
                        call.currencies.AUD,
                        call.currencies.JPY,
                        call.currencies.CNY,
                        call.currencies.BTC
                    )
                )
                listaDeMoedas.postValue(listaNovaMoedas)

            } catch (e: Exception) {
                mensagemDeErro.postValue("Não foi possível carregar as informações.")

            }
        }

    }





}