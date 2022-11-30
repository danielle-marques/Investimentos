package com.example.investimentos.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.doOnTextChanged
import com.example.investimentos.R
import com.example.investimentos.SimulacaoValores.buscaValoresHashmap
import com.example.investimentos.SimulacaoValores.modificaValoresHashmap
import com.example.investimentos.SimulacaoValores.operacaoSimulado
import com.example.investimentos.SimulacaoValores.saldoDisponivel
import com.example.investimentos.SimulacaoValores.toolbarSimulado
import com.example.investimentos.base.BaseActivity
import com.example.investimentos.databinding.ActivityCambioBinding
import com.example.investimentos.extensions.*
import com.example.investimentos.model.MoedaModel
import com.example.investimentos.util.FuncoesUteis


class Cambio : BaseActivity() {

    private val binding by lazy {
        ActivityCambioBinding.inflate(layoutInflater)
    }
    private var moedaSelecionada: MoedaModel? = null
    private var quantidade = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        configuraToolbar(
            binding.includeToolbarCambio.baseToolbarTv,
            binding.includeToolbarCambio.imgVoltaBase,
            CAMBIO
        )
        recebeMoedaSelecionada()

    }

    override fun onResume() {
        super.onResume()
        binding.edtQuantidadeMoedasCambio.text?.clear()
        recebeMoedaSelecionada()
    }

    private fun recebeMoedaSelecionada() {
        moedaSelecionada = intent.getSerializableExtra(NOME_MOEDA) as? MoedaModel
        moedaSelecionada?.let {
            preencheCamposMoedas(it)
            configuraEditTextQuantidade(it)
        }
    }

    private fun preencheCamposMoedas(moeda: MoedaModel) {

        if (moeda.compra == null) moeda.compra = 0.0
        if (moeda.venda == null) moeda.venda = 0.0
        FuncoesUteis.alteraCorDaPorcentagem(binding.tvPorcentagemMoedaCambio, moeda)
        binding.tvNomeMoedaCambio.text = buildString {
            append(
                moeda.isoMoeda,
                getString(R.string.hifen),
                moeda.moeda
            )
        }
        binding.tvPorcentagemMoedaCambio.text = formataPorcentagem(moeda.porcentagem)
        binding.tvCompraMoedaCambio.text = buildString {
            append(
                getString(R.string.compra_espaco),
                formataMoeda(moeda.compra)
            )
        }
        binding.tvVendaMoedaCambio.text = buildString {
            append(
                getString(R.string.venda_espaco),
                formataMoeda(moeda.venda)
            )

        }
        binding.tvSaldoDisponivelCambio.text = buildString {
            append(
                getString(R.string.saldo_disponivel),
                formataMoeda(saldoDisponivel)
            )

        }
        binding.tvValorDaMoedaEmCaixaCambio.text = buildString {
            append(
                buscaValoresHashmap(moeda),
                getString(R.string.espaco),
                moeda.moeda,
                getString(R.string.em_caixa)
            )
        }
    }

    private fun configuraEditTextQuantidade(moeda: MoedaModel) {
        binding.edtQuantidadeMoedasCambio.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotBlank()) {
                quantidade = text.toString().toInt()
                validarCompra(moeda, quantidade)
                validarVenda(moeda, quantidade)
            } else {
                desabilitaBotao(binding.botaoComprarCambio)
                desabilitaBotao(binding.botaoVenderCambio)
            }
        }
    }

    private fun habilitaBotao(botao: AppCompatButton) {
        botao.isEnabled = true
        botao.setBackgroundResource(R.drawable.fundo_botao)
    }

    private fun desabilitaBotao(botao: AppCompatButton) {
        botao.isEnabled = false
        botao.setBackgroundResource(R.drawable.fundo_botao_desabilitado)
    }

    private fun validarVenda(moeda: MoedaModel, quantidade: Int) {
        val vender = binding.botaoVenderCambio

        if (quantidade <= buscaValoresHashmap(moeda) && quantidade != 0 && moeda.venda != 0.0) {
            habilitaBotao(vender)
            configuraBotaoVender(moeda, quantidade)
        } else {
            desabilitaBotao(vender)
        }
    }

    private fun validarCompra(moeda: MoedaModel, quantidade: Int) {
        moeda.compra?.let { valorCompra ->
            val comprar = binding.botaoComprarCambio
            if (quantidade * valorCompra <= saldoDisponivel && quantidade != 0 && moeda.compra != 0.0) {
                habilitaBotao(comprar)
                configuraBotaoComprar(moeda, quantidade)
            } else {
                desabilitaBotao(comprar)
            }
        }
    }

    private fun configuraBotaoComprar(moeda: MoedaModel, quantidade: Int) {
        binding.botaoComprarCambio.setOnClickListener {
            moeda.compra?.let { valorCompra ->
                modificaValoresHashmap(moeda.isoMoeda, COMPRAR, quantidade)
                val totalValor = quantidade * valorCompra
                saldoDisponivel -= totalValor
                finalizaOperacao(
                    TelaFinalizacao::class.java,
                    getString(R.string.comprar_maiusculo),
                    getString(R.string.comprar_minusculo),
                    totalValor
                )
            }
        }
    }

    private fun configuraBotaoVender(moeda: MoedaModel, quantidade: Int) {
        binding.botaoVenderCambio.setOnClickListener {
            moeda.venda?.let { valorVenda ->
                modificaValoresHashmap(moeda.isoMoeda, VENDER, quantidade)
                val totalValor = quantidade * valorVenda
                saldoDisponivel += totalValor
                finalizaOperacao(
                    TelaFinalizacao::class.java,
                    getString(R.string.vender_maiusculo),
                    getString(R.string.vender_minusculo),
                    totalValor
                )
            }
        }
    }

    private fun finalizaOperacao(
        clazz: Class<*>,
        toolbar: String,
        operacao: String,
        total: Double
    ) {
        Intent(this, clazz).apply {
            operacaoSimulado = operacao
            toolbarSimulado = toolbar
            putExtra(VALOR_TOTAL, total)
            putExtra(QUANTIDADE_MOEDA, quantidade)
            putExtra(MOEDA_SELECIONADA, moedaSelecionada)
            startActivity(this)
        }
    }
}


