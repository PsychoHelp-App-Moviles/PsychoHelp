package com.codetech.psychohelp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetech.psychohelp.databinding.ActivityLogbookBinding
import com.codetech.psychohelp.patient.PatientAdapter
import com.codetech.psychohelp.patient.PatientClickListener
import com.codetech.psychohelp.patient.PatientsResponse
import com.codetech.psychohelp.psychologist.PsychologistLogbook
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val ID = "com.codetech.psychohelp.MESSAGE"

class Patients : AppCompatActivity(), PatientClickListener {
    private lateinit var  binding: ActivityLogbookBinding;
    private lateinit var adapter : PatientAdapter;
    private val patientsList = mutableListOf<PatientsResponse>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogbookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPatients("1")
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = PatientAdapter(patientsList, this)
        binding.rvListPatients.layoutManager = LinearLayoutManager(this)
        binding.rvListPatients.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getPatients(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getPatientsByPsychologistId(id)
            val patients = call.body()
            runOnUiThread{
                if(call.isSuccessful) {
                    patientsList.clear()
                    patientsList.addAll(patients!!)
                    adapter.notifyDataSetChanged()
                }else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(this, "What error is this", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(patient: PatientsResponse) {
        val intent = Intent(this, PsychologistLogbook::class.java)
        intent.putExtra(ID, patient.id)
        startActivity(intent)
    }
}