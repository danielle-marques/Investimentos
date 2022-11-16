package com.example.investimentos.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.investimentos.SingletonSimulacaoValores.saldoDisponivel
import com.example.investimentos.databinding.ActivityCambioBinding
import com.example.investimentos.extensions.*
import com.example.investimentos.model.MainViewModelFactory
import com.example.investimentos.model.MoedaModel
import com.example.investimentos.model.MoedaViewModel
import com.example.investimentos.repositories.MoedaRepository
import com.example.investimentos.util.FuncoesUteis
import java.math.RoundingMode


class Cambio : AppCompatActivity() {

    private val binding by lazy {
        ActivityCambioBinding.inflate(layoutInflater)
    }
    private var moedaSelecionada: MoedaModel? = null
    private lateinit var moedaViewModel: MoedaViewModel
    private var quantidade = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        moedaViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(MoedaRepository())
        )[MoedaViewModel::class.java]


        configuraToolbar()
       // FuncoesUteis.increaseTouch(binding.includeToolbarCambio.imgVoltaHomeToolbar, 150F)


    }


    override fun onResume() {
        super.onResume()
        binding.edtQuantidadeMoedasCambio.text?.clear()
        recebeMoeda()


    }

    private fun configuraToolbar() {
        setSupportActionBar(binding.includeToolbarCambio.toolbarCambio)
        binding.includeToolbarCambio.imgVoltaHomeToolbar.setOnClickListener { finish() }
        setSupportActionBar(binding.includeToolbarCambio.toolbarCambio)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }


    private fun recebeMoeda() {
        moedaSelecionada = intent.getSerializableExtra(NOME_MOEDA) as? MoedaModel
        moedaSelecionada?.let { moeda ->
            if (moeda.saldoMoedaEmCaixa == 0) {
                FuncoesUteis.buscaValorSimuladoParaModel(moeda)
            }

            binding.tvNomeMoedaCambio.text =
                "${moeda.isoMoeda} - ${moeda.moeda}"
            binding.tvPorcentagemMoedaCambio.text =
                moeda.porcentagem.toString()
            if (moeda.compra == null) {
                binding.tvCompraMoedaCambio.text = "Compra: R$ 0,00"
            } else {
                binding.tvCompraMoedaCambio.text =
                    "Compra: R$ ${
                        moeda.compra.toString().toBigDecimal().setScale(2, RoundingMode.UP)
                    }"
            }
            if (moeda.venda == null) {
                binding.tvVendaMoedaCambio.text = "Venda: R$ 0,00"
            } else {
                binding.tvVendaMoedaCambio.text =
                    "Venda: R$ ${
                        moeda.venda.toString().toBigDecimal().setScale(2, RoundingMode.DOWN)
                    }"
            }

            binding.tvValorDaMoedaEmCaixaCambio.text =
                "${moeda.saldoMoedaEmCaixa} ${moeda.moeda} em caixa"
            binding.tvSaldoDisponivelCambio.text =
                "Saldo disponível: R$ ${
                    saldoDisponivel.toBigDecimal().setScale(2, RoundingMode.UP)
                }"

            FuncoesUteis.incicializarPorcentagemCor(binding.tvPorcentagemMoedaCambio, moeda)



            binding.edtQuantidadeMoedasCambio.doOnTextChanged { text, _, _, _ ->
                if (text.toString().isNotBlank()) {
                    quantidade = text.toString().toInt()
                    configuraCompra(moeda, quantidade)
                    configuraVenda(moeda, quantidade)

                } else {
                    moedaViewModel.desabilitaBotao(binding.botaoComprarCambio)
                    moedaViewModel.desabilitaBotao(binding.botaoVenderCambio)
                }
            }

        }
    }

    private fun configuraVenda(moeda: MoedaModel, quantidade: Int) {
        if (quantidade.toString().isNotBlank() && moeda.venda != null) {

            if (FuncoesUteis.validaQuantidadeNaVenda(
                    quantidade,
                    moeda
                )
            ) {
                moedaViewModel.habilitaBotao(binding.botaoVenderCambio)
                configuraBotaoVender(moeda, quantidade)

            } else {
                moedaViewModel.desabilitaBotao(binding.botaoVenderCambio)

            }
        } else {
            moedaViewModel.desabilitaBotao(binding.botaoVenderCambio)

        }
    }

    private fun configuraCompra(moeda: MoedaModel, quantidade: Int) {
        if (quantidade.toString().isNotBlank() && moeda.compra != null) {
            if (FuncoesUteis.validaQuantidadeNaCompra(
                    quantidade,
                    moeda
                )
            ) {
                moedaViewModel.habilitaBotao(binding.botaoComprarCambio)
                configuraBotaoComprar(moeda, quantidade)

            } else {
                moedaViewModel.desabilitaBotao(binding.botaoComprarCambio)

            }
        } else {
            moedaViewModel.desabilitaBotao(binding.botaoComprarCambio)

        }
    }

    private fun configuraBotaoComprar(moeda: MoedaModel, quantidade: Int) {

        binding.botaoComprarCambio.setOnClickListener {
            moeda.saldoMoedaEmCaixa += quantidade
            FuncoesUteis.calculoOperacao(moeda)
            val totalValorComprado = quantidade * moeda.compra!!
            saldoDisponivel -= totalValorComprado

            Intent(this, TelaFinalizacao::class.java).apply {
                putExtra(VALOR_TOTAL_DA_COMPRA, totalValorComprado)
                putExtra(VALOR_COMPRA, quantidade)
                putExtra(COMPRA_MOEDA, moedaSelecionada)
                startActivity(this)
            }
        }
    }

    private fun configuraBotaoVender(moeda: MoedaModel, quantidade: Int) {

        binding.botaoVenderCambio.setOnClickListener {
            moeda.saldoMoedaEmCaixa -= quantidade
            FuncoesUteis.calculoOperacao(moeda)
            val totalValorVendido = quantidade * moeda.venda!!
            saldoDisponivel += totalValorVendido

            Intent(this, TelaFinalizacao::class.java).apply {
                putExtra(VALOR_TOTAL_DA_VENDA, totalValorVendido)
                putExtra(VALOR_VENDA, quantidade)
                putExtra(VENDA_MOEDA, moedaSelecionada)
                startActivity(this)

            }
        }
    }


}


