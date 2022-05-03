package com.codetech.psychohelp.patient

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.codetech.psychohelp.databinding.ItemPatientBinding
import com.codetech.psychohelp.patient.PatientClickListener
import com.codetech.psychohelp.patient.PatientsResponse
import com.squareup.picasso.Picasso

class PatientViewHolder(view: View, private val clickListener: PatientClickListener):RecyclerView.ViewHolder(view) {

    private val binding = ItemPatientBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun bind(patientsResponse: PatientsResponse){
        Picasso.get().load(patientsResponse.img).into(binding.ivPatient)
        binding.tvEmailPatient.text = patientsResponse.email
        binding.tvNamePatient.text = patientsResponse.firstName + " " + patientsResponse.lastName

        binding.cvPatient.setOnClickListener{
            clickListener.onClick(patientsResponse)
        }

    }
}