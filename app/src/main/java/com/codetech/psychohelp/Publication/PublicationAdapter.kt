package com.codetech.psychohelp.Publication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codetech.psychohelp.R

class PublicationAdapter (private val publicationList: List<PublicationsResponse>): RecyclerView.Adapter<PublicationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PublicationViewHolder(layoutInflater.inflate(R.layout.publication_view, parent, false))
    }

    override fun onBindViewHolder(holder: PublicationViewHolder, position: Int) {
        val item: PublicationsResponse = publicationList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return publicationList.size
    }
}