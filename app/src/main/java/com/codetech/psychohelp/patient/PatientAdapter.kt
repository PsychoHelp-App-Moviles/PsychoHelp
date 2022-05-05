package com.codetech.psychohelp.patient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codetech.psychohelp.R

class PatientAdapter(private val patientList: List<PatientsResponse>, private val clickListener: PatientClickListener) : RecyclerView.Adapter<PatientViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PatientViewHolder(layoutInflater.inflate(R.layout.item_patient, parent, false), clickListener)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val item: PatientsResponse = patientList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
       return patientList.size
    }

}