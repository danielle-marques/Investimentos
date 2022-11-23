package com.example.investimentos.base

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.lifecycle.ViewModelProvider
import com.example.investimentos.databinding.ActivityBaseBinding
import com.example.investimentos.extensions.MOEDAS
import com.example.investimentos.model.MainViewModelFactory
import com.example.investimentos.model.MoedaViewModel
import com.example.investimentos.repositories.MoedaRepository

abstract class BaseActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityBaseBinding.inflate(layoutInflater)

    }


    lateinit var moedaViewModel: MoedaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        moedaViewModel()

    }

    protected open fun moedaViewModel() {
        moedaViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(MoedaRepository())
        )[MoedaViewModel::class.java]
        moedaViewModel.mensagemDeErro.observe(this) { mensagem ->
            Toast.makeText(applicationContext, mensagem, Toast.LENGTH_LONG).show()
        }
    }


    protected open fun configuraToolbar(tvTitulo: TextView, btnVoltar: ImageButton, titulo: String) {
        setSupportActionBar(binding.includeToolbarBase.toolbarBase)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setIsHeading(binding.includeToolbarBase.baseToolbarTv)
        tvTitulo.text = titulo
        if (titulo == MOEDAS) {
            btnVoltar.visibility = View.GONE
        } else {
            btnVoltar.visibility = View.VISIBLE
            btnVoltar.setOnClickListener { finish() }
        }
    }

    private fun setIsHeading(textView: TextView) {
        ViewCompat.setAccessibilityDelegate(textView, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    host.isAccessibilityHeading = true
                } else {
                    info.isHeading = true
                }
            }
        })
    }


}