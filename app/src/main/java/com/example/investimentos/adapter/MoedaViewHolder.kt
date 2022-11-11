package com.example.investimentos.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.investimentos.databinding.ItemMoedaBinding
import com.example.investimentos.model.MoedaModel
import com.example.investimentos.util.FuncoesUteis

class MoedaViewHolder(
    private val binding: ItemMoedaBinding,
    private val quandoClicarNaMoeda: (moedaModel: MoedaModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var moedaModel: MoedaModel

    init {
        itemView.setOnClickListener {
            if (::moedaModel.isInitialized) {
                quandoClicarNaMoeda(moedaModel)
            }
        }

    }

    fun vinculaMoedas(moedaModel: MoedaModel) {

        binding.itemMoeda.text = moedaModel.isoMoeda
        binding.itemPorcentagem.text = moedaModel.porcentagem.toString()
        FuncoesUteis.incicializarPorcentagemCor(binding.itemPorcentagem, moedaModel)
    }

}