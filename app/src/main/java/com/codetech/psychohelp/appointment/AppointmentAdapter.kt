package com.codetech.psychohelp.appointment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codetech.psychohelp.R

class AppointmentAdapter(private val appointmentsList: List<AppointmentsResponse>, private val clickListener: AppointmentClickListener)
    : RecyclerView.Adapter<AppointmentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AppointmentViewHolder(layoutInflater.inflate(R.layout.item_appointment, parent, false), clickListener)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val item: AppointmentsResponse = appointmentsList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return appointmentsList.size
    }
}