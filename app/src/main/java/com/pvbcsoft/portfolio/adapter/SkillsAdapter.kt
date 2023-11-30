package com.pvbcsoft.portfolio.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pvbcsoft.portfolio.databinding.SkillsItemBinding
import com.pvbcsoft.portfolio.model.Skills

class SkillsAdapter(private val skillsList: ArrayList<Skills>) :
    RecyclerView.Adapter<SkillsAdapter.SkillsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillsViewHolder {
        val binding = SkillsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SkillsViewHolder(binding)
    }

    override fun getItemCount() = skillsList.size

    override fun onBindViewHolder(holder: SkillsViewHolder, position: Int) {
        val currentItem = skillsList[position]

        holder.binding.apply {
            tvLogoName.text = currentItem.logoName

            Glide.with(root.context)
                .load(currentItem.logo)
                .into(ivLogo)
        }
    }

    class SkillsViewHolder(val binding: SkillsItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}