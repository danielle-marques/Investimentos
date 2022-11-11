package com.example.investimentos.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.investimentos.adapter.MoedasAdapter
import com.example.investimentos.databinding.ActivityHomeBinding
import com.example.investimentos.extensions.NOME_MOEDA
import com.example.investimentos.model.MainViewModelFactory
import com.example.investimentos.model.MoedaViewModel
import com.example.investimentos.repositories.MoedaRepository

class Home : AppCompatActivity() {

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val moedasAdapter by lazy {
        MoedasAdapter()
    }

    private lateinit var moedaViewModel: MoedaViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.includeToolbarMoeda.toolbarHome)
        moedaViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(MoedaRepository())
        )[MoedaViewModel::class.java]
        moedaViewModel.mensagemDeErro.observe(this) { mensagem ->
            Toast.makeText(applicationContext, mensagem, Toast.LENGTH_LONG).show()
        }
        configrarRecyclerView()

        setupObserver()

        configuraToolbar()

    }

    private fun configuraToolbar() {
        setSupportActionBar(binding.includeToolbarMoeda.toolbarHome)
        supportActionBar?.setDisplayShowTitleEnabled(false)
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