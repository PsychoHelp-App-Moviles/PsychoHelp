package com.codetech.psychohelp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codetech.psychohelp.fragments.PsychologistsFragment
import com.codetech.psychohelp.patient.PatientAdapter
import com.codetech.psychohelp.patient.PatientClickListener
import com.codetech.psychohelp.patient.PatientViewHolder
import com.codetech.psychohelp.patient.PatientsResponse
import com.codetech.psychohelp.psychologist.PsychologistLogbook
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val ID = "com.codetech.psychohelp.MESSAGE"

class ListOfPatients : Fragment(), PatientClickListener {


    private var adapter: RecyclerView.Adapter<PatientViewHolder> ?= null
    private val patientsList = mutableListOf<PatientsResponse>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                          savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_list_of_patients, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        getPatients("1")
        view?.findViewById<RecyclerView>(R.id.rvListPatients).apply{
            adapter = PatientAdapter(patientsList, this@ListOfPatients)
            this?.layoutManager = LinearLayoutManager(activity)
            this?.adapter = adapter
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://psychohelp.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getPatients(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getPatientsByPsychologistId(id)
            val patients = call.body()

            activity?.runOnUiThread{
                if(call.isSuccessful) {
                    patientsList.clear()
                    patientsList.addAll(patients!!)
                    adapter?.notifyDataSetChanged()
                }else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText( context,"An error has been occurred while processing your request", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(patient: PatientsResponse) {
        val intent = Intent(activity, PsychologistLogbook::class.java).apply {
            putExtra(ID, patient.id)
        }
        Log.d("patient", patient.id)
        activity?.startActivity(intent)
    }

    companion object {

        @JvmStatic
        fun newInstance() = PsychologistsFragment()
    }
}