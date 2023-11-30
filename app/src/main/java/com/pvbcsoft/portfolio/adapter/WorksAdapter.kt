package com.pvbcsoft.portfolio.adapter


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pvbcsoft.portfolio.databinding.WorksItemBinding
import com.pvbcsoft.portfolio.model.Works

class WorksAdapter(private val context: Context, private val worksList: ArrayList<Works>) :
    RecyclerView.Adapter<WorksAdapter.WorksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorksViewHolder {
        val binding = WorksItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WorksViewHolder(binding)
    }

    override fun getItemCount() = worksList.size

    override fun onBindViewHolder(holder: WorksViewHolder, position: Int) {
        val currentItem = worksList[position]

        holder.binding.apply {
            tvWorkName.text = currentItem.workName
            tvWorkDesc.text = currentItem.workDesc

            Glide.with(root.context)
                .load(currentItem.workLogo)
                .into(ivWorkLogo)

            btnWorkApplication.setOnClickListener {
                val applicationLink = currentItem.workApplicationLink
                if (!applicationLink.isNullOrEmpty()) {
                    LinkUtils.openLink(context, applicationLink)
                }
            }

            btnWorkRepository.setOnClickListener {
                val repositoryLink = currentItem.workRepositoryLink
                if (!repositoryLink.isNullOrEmpty()) {
                    LinkUtils.openLink(context, repositoryLink)
                }
            }
        }
    }

    class WorksViewHolder(val binding: WorksItemBinding) : RecyclerView.ViewHolder(binding.root)

    object LinkUtils {
        fun openLink(context: Context, link: String) {
            val uri = Uri.parse(link)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }
    }
}