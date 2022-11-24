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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        toolbarSimulado?.let {
            configuraToolbar(
                binding.includeToolbarFinaliza.baseToolbarTv,
                binding.includeToolbarFinaliza.imgVoltaBase,
                it
            )
        }

        realizaOperacao()
    }


    private fun realizaOperacao() {
        moedaSelecionada = intent.getSerializableExtra(MOEDA_SELECIONADA) as? MoedaModel
        binding.includeToolbarFinaliza.baseToolbarTv.text = "$toolbarSimulado"
        val valorTotal = intent.getDoubleExtra(VALOR_TOTAL, 0.0)
        val quantidadeMoeda = intent.getIntExtra(QUANTIDADE_MOEDA, 0)
        val operacao = operacaoSimulado
        val isoMoeda = moedaSelecionada?.isoMoeda
        val moedaNome = moedaSelecionada?.moeda
        moedaSelecionada?.let {
            binding.textoFinalizacao.text = buildString {
                append(
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

            }
            botaoVoltarTelaHome()
        }

    }

    private fun botaoVoltarTelaHome() {
        binding.botaoVoltarHome.setOnClickListener {
            Intent(this, Home::class.java).let {
                finish()
                startActivity(it)
            }
        }
    }
}