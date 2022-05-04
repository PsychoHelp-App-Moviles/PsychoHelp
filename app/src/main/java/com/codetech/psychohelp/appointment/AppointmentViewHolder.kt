package com.codetech.psychohelp.appointment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.codetech.psychohelp.databinding.ItemAppointmentBinding

class AppointmentViewHolder(view: View, private val clickListener: AppointmentClickListener) : RecyclerView.ViewHolder(view) {

    private val binding2 = ItemAppointmentBinding.bind(view)


    fun bind(appointment: AppointmentsResponse) {
        binding2.tvDateSession.text = appointment.scheduleDate

        binding2.cvSessionDate.setOnClickListener{
            clickListener.onClick(appointment)
        }
    }
}