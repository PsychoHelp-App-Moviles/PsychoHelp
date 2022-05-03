package com.codetech.psychohelp.psychologist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetech.psychohelp.APIService
import com.codetech.psychohelp.ID
import com.codetech.psychohelp.patient.PatientsResponse
import com.codetech.psychohelp.R
import com.codetech.psychohelp.appointment.AppointmentAdapter
import com.codetech.psychohelp.appointment.AppointmentClickListener
import com.codetech.psychohelp.appointment.AppointmentsResponse
import com.codetech.psychohelp.databinding.ActivityLogbookBinding
import com.codetech.psychohelp.databinding.ActivityPsychologistLogbookBinding
import com.codetech.psychohelp.patient.PatientAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PsychologistLogbook : AppCompatActivity(), AppointmentClickListener {

    private lateinit var patientResponse : PatientsResponse
    private lateinit var appointmentResponse : AppointmentsResponse
    private val appointmentList = mutableListOf<AppointmentsResponse>();
    private lateinit var  binding: ActivityPsychologistLogbookBinding;
    private lateinit var adapter : AppointmentAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPsychologistLogbookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra(ID)
        val textView = findViewById<TextView>(R.id.tvIdPatient).apply {
            text = id
        }
        getPatient(id!!)
        getAppointments(id!!)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = AppointmentAdapter(appointmentList, this)
        binding.rvListAppointments.layoutManager = LinearLayoutManager(this)
        binding.rvListAppointments.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getPatient(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getPatientById(id)
            val patient = call.body()
            runOnUiThread{
                if(call.isSuccessful) {
                    patientResponse = patient!!;
                }else {
                    showError()
                }
            }
        }
    }

    private fun getAppointments(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getAllAppointmentsByPatientIdAndPsychologistId("1",id)
            val appointments = call.body()
            runOnUiThread{
                if(call.isSuccessful) {
                    appointmentList.clear()
                    appointmentList.addAll(appointments!!)
                    adapter.notifyDataSetChanged()
                }else {
                    showError()
                }
            }
        }
    }

    private fun getAppointment(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getAppointmentById(id)
            val appointment = call.body()
            runOnUiThread{
                if(call.isSuccessful) {
                    appointmentResponse = appointment!!;
                }else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(this, "What error is this", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(appointment: AppointmentsResponse) {
        getAppointment(appointment.id)
    }
}