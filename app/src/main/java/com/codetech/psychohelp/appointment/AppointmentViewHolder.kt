package com.codetech.psychohelp.appointment
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.codetech.psychohelp.databinding.CardviewBinding

class AppointmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = CardviewBinding.bind(view)

    fun bind(appointment: AppointmentsResponse) {
        binding.tvDate.text = appointment.scheduleDate
    }
}