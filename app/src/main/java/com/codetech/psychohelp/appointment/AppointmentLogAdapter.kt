package com.codetech.psychohelp.appointment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codetech.psychohelp.R

class AppointmentLogAdapter(private val appointmentsList: List<AppointmentsResponse>, private val clickListener: AppointmentClickListener)
    : RecyclerView.Adapter<AppointmentLogViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentLogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AppointmentLogViewHolder(layoutInflater.inflate(R.layout.item_appointment, parent, false), clickListener)
    }

    override fun onBindViewHolder(holder: AppointmentLogViewHolder, position: Int) {
        val item: AppointmentsResponse = appointmentsList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return appointmentsList.size
    }
}