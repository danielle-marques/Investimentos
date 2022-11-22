package com.example.investimentos.ui.activity

import android.content.Intent
import android.os.Bundle
import com.example.investimentos.R
import com.example.investimentos.SimulacaoValores.operacaoSimulado
import com.example.investimentos.SimulacaoValores.toolbarSimulado
import com.example.investimentos.base.BaseActivity
import com.example.investimentos.databinding.ActivityTelaFinalizacaoBinding
import com.example.investimentos.extensions.MOEDA_SELECIONADA
import com.example.investimentos.extensions.QUANTIDADE_MOEDA
import com.example.investimentos.extensions.VALOR_TOTAL
import com.example.investimentos.extensions.formataMoeda
import com.example.investimentos.model.MoedaModel

class TelaFinalizacao : BaseActivity() {

    private val binding by lazy {
        ActivityTelaFinalizacaoBinding.inflate(layoutInflater)

    }

    private var moedaSelecionada: MoedaModel? = null

    private val operacaoRealizada = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.includeToolbarFinaliza.imgVoltaCambioToolbar.setOnClickListener { finish() }
        configuraToolbar()
        moedaViewModel()
        realizaOperacao()
    }


    private fun botaoVoltarTelaHome() {
        binding.botaoVoltarHome.setOnClickListener {
            Intent(this, Home::class.java).let {
                finish()
                startActivity(it)
            }
        }
    }


    private fun realizaOperacao() {
        moedaSelecionada = intent.getSerializableExtra(MOEDA_SELECIONADA) as? MoedaModel
        binding.includeToolbarFinaliza.venderComprarToolbarFinaliza.text = "$toolbarSimulado"
        val valorTotal = intent.getDoubleExtra(VALOR_TOTAL, 0.0)
        val quantidadeMoeda = intent.getIntExtra(QUANTIDADE_MOEDA, 0)
        val operacao = operacaoSimulado
        val isoMoeda = moedaSelecionada?.isoMoeda
        val moedaNome = moedaSelecionada?.moeda
        moedaSelecionada?.let {
            operacaoRealizada.let {
                it.append(binding.textoFinalizacao.text)
                    .append(
                        getString(R.string.parabens),
                        operacao,
                        getString(R.string.espaco),
                        quantidadeMoeda,
                        getString(R.string.espaco),
                        isoMoeda,
                        getString(R.string.hifen),
                        moedaNome,
                        getString(R.string.virgula),
                        getString(R.string.totalizando),
                        formataMoeda(valorTotal)
                    )
                    .toString()

                binding.textoFinalizacao.text = it
            }
            botaoVoltarTelaHome()
        }


    }
}