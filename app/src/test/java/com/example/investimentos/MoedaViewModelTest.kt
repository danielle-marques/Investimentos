package com.example.investimentos

import com.example.investimentos.api.Endpoint
import com.example.investimentos.model.MoedaModel
import com.example.investimentos.model.MoedaViewModel
import com.example.investimentos.repositories.MoedaRepository
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test


class MoedaViewModelTest : BaseTest() {

    private val repositorio = mockk<MoedaRepository>(relaxUnitFun = true)
    private var viewModel: MoedaViewModel = MoedaViewModel(repositorio)


    @Test
    fun deveRetornarMoeda_QuandoChamadaListaDoRepositorio() {
        val resultado = Endpoint().apply {
            this.currencies.USD = MoedaModel(
                moeda = "Dollar",
                porcentagem = 0.0,
                compra = 1.0,
                venda = 2.50,
                isoMoeda = "USD"
            )
        }
        val listaDeMoedaEsperada = listOfNotNull(
            resultado.currencies.USD,
            resultado.currencies.EUR,
            resultado.currencies.ARS,
            resultado.currencies.CAD,
            resultado.currencies.GBP,
            resultado.currencies.AUD,
            resultado.currencies.JPY,
            resultado.currencies.CNY,
            resultado.currencies.BTC
        )

        coEvery { repositorio.buscaMoedaRepository() } returns resultado

        viewModel.atualizadorDeMoedas()

        assertEquals(listaDeMoedaEsperada, viewModel.listaDeMoedas.value)


    }

    @Test
    fun quandoChamarApi_deveRetornarErro() {

        coEvery { repositorio.buscaMoedaRepository() } throws Exception("Não foi possível carregar as informações.")


        viewModel.atualizadorDeMoedas()
        assertEquals(
            "Não foi possível carregar as informações.",
            viewModel.mensagemDeErro.value
        )


    }
}

