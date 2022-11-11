package com.example.investimentos.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.investimentos.databinding.ActivityTelaFinalizacaoBinding
import com.example.investimentos.extensions.*
import com.example.investimentos.model.MainViewModelFactory
import com.example.investimentos.model.MoedaModel
import com.example.investimentos.model.MoedaViewModel
import com.example.investimentos.repositories.MoedaRepository
import com.example.investimentos.util.FuncoesUteis
import java.math.RoundingMode

class TelaFinalizacao : AppCompatActivity() {

    private val binding by lazy {
        ActivityTelaFinalizacaoBinding.inflate(layoutInflater)

    }

    private var moedaSelecionada: MoedaModel? = null
    private lateinit var moedaViewModel: MoedaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        moedaViewModel = ViewModelProvider(this, MainViewModelFactory(MoedaRepository()))[MoedaViewModel::class.java]

        configuraToolbar()

        comprarMoeda()

        venderMoeda()

        botaoVoltarTelaHome()

        FuncoesUteis.increaseTouch(binding.includeToolbarFinaliza.imgVoltaCambioToolbar, 150F)
    }

    private fun botaoVoltarTelaHome() {
        binding.botaoVoltarHome.setOnClickListener {
            Intent(this, Home::class.java).let {
                finish()
                startActivity(it)
            }
        }
    }

    private fun configuraToolbar() {
        setSupportActionBar(binding.includeToolbarFinaliza.toolbarFinaliza)
        binding.includeToolbarFinaliza.imgVoltaCambioToolbar.setOnClickListener { finish() }
        setSupportActionBar(binding.includeToolbarFinaliza.toolbarFinaliza)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    @SuppressLint("SetTextI18n")
    private fun venderMoeda() {

        moedaSelecionada = intent.getSerializableExtra(VENDA_MOEDA) as? MoedaModel
        val valorVENDA = intent.getIntExtra(VALOR_VENDA, 0)
        val valorTotalDaVenda = intent.getDoubleExtra(VALOR_TOTAL_DA_VENDA, 0.0)
        moedaSelecionada?.let { moeda ->
            binding.includeToolbarFinaliza.venderComprarToolbarFinaliza.text = "Vender"
            binding.textoFinalizacao.text = "Parabéns! \n" +
                    "Você acabou de vender $valorVENDA ${moeda.moeda} - ${moeda.isoMoeda}, totalizando \n" +
                    "R$ ${valorTotalDaVenda.toBigDecimal().setScale(2, RoundingMode.UP)}"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun comprarMoeda() {

        moedaSelecionada = intent.getSerializableExtra(COMPRA_MOEDA) as? MoedaModel
        val valorCompra = intent.getIntExtra(VALOR_COMPRA, 0)
        val valorTotalDaCompra = intent.getDoubleExtra(VALOR_TOTAL_DA_COMPRA, 0.0)

        moedaSelecionada?.let { moeda ->
            binding.includeToolbarFinaliza.venderComprarToolbarFinaliza.text = "Comprar"
            binding.textoFinalizacao.text = "Parabéns! \n" +
                    "Você acabou de comprar $valorCompra ${moeda.moeda} - ${moeda.isoMoeda}, totalizando \n" +
                    "R$ ${valorTotalDaCompra.toBigDecimal().setScale(2, RoundingMode.UP)}"
        }
    }


}