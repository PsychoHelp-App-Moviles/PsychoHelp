package com.codetech.psychohelp.Publication

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.codetech.psychohelp.databinding.CardviewBinding
import com.codetech.psychohelp.databinding.PublicationViewBinding

class PublicationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = PublicationViewBinding.bind(view)

    fun bind(publication: PublicationsResponse) {
        binding.tvTitle.text = publication.title
        binding.tvDescription.text = publication.description
    }
}