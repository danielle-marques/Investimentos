package com.example.investimentos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.investimentos.databinding.ItemMoedaBinding
import com.example.investimentos.model.MoedaModel

class MoedasAdapter(
    var quandoClicaNaMoeda: (moedaModel: MoedaModel) -> Unit = {},
    listaMoedas: List<MoedaModel?> = emptyList()

) : RecyclerView.Adapter<MoedaViewHolder>() {
    private val listaMoedas = listaMoedas.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoedaViewHolder = MoedaViewHolder(
        ItemMoedaBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        quandoClicaNaMoeda
    )

    override fun onBindViewHolder(holder: MoedaViewHolder, position: Int) {
        listaMoedas[position]?.let { moeda ->
            holder.vinculaMoedas(moeda)
            holder.itemView.setOnClickListener { quandoClicaNaMoeda.invoke(moeda) }

        }
    }


    override fun getItemCount(): Int = listaMoedas.size

    fun refresh(newList: List<MoedaModel?>) {
        listaMoedas.clear()
        listaMoedas.addAll(newList)
        notifyDataSetChanged()
    }

}