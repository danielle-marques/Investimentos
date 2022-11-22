package com.example.investimentos.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.investimentos.adapter.MoedasAdapter
import com.example.investimentos.base.BaseActivity
import com.example.investimentos.databinding.ActivityHomeBinding
import com.example.investimentos.extensions.NOME_MOEDA

class Home : BaseActivity() {

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val moedasAdapter by lazy {
        MoedasAdapter()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        moedaViewModel()

        configrarRecyclerView()

        setupObserver()

        configuraToolbar()

    }

    private fun setupObserver() {
        moedaViewModel.listaDeMoedas.observe(this) { moedas ->
            moedasAdapter.refresh(moedas)
        }
        moedaViewModel.atualizadorDeMoedas()
    }

    private fun configrarRecyclerView() {
        binding.listaMoedasHomeRecyclerview.adapter = moedasAdapter
        binding.listaMoedasHomeRecyclerview.layoutManager = LinearLayoutManager(this)
        moedasAdapter.quandoClicaNaMoeda = { moeda ->
            Intent(this, Cambio::class.java).apply {

                putExtra(NOME_MOEDA, moeda)
                startActivity(this)
            }
        }
    }


}