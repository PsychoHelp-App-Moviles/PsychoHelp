package com.codetech.psychohelp.patient

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.codetech.psychohelp.databinding.ItemPatientBinding
import com.squareup.picasso.Picasso

class PatientViewHolder(view: View, private val clickListener: PatientClickListener):RecyclerView.ViewHolder(view) {

    private val binding = ItemPatientBinding.bind(view)

    fun bind(patientsResponse: PatientsResponse){
        Picasso.get().load(patientsResponse.img).into(binding.ivPatient)
        binding.tvEmailPatient.text = patientsResponse.email
        val name = patientsResponse.firstName + " " + patientsResponse.lastName
        binding.tvNamePatient.text = name
        binding.cvPatient.setOnClickListener{
            clickListener.onClick(patientsResponse)
        }
    }
}