package com.codetech.psychohelp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetech.psychohelp.appointment.AppointmentAdapter
import com.codetech.psychohelp.appointment.AppointmentsResponse
import com.codetech.psychohelp.databinding.ActivityHistoryAppointmentsPsychologistBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistoryAppointmentsPsychologist : AppCompatActivity() {
    private lateinit var  binding: ActivityHistoryAppointmentsPsychologistBinding;
    private lateinit var adapter : AppointmentAdapter;
    private val appointmentsList = mutableListOf<AppointmentsResponse>();

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityHistoryAppointmentsPsychologistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getAppointmentByPatientId("1")
        initRecyclerView()
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun initRecyclerView() {
        adapter = AppointmentAdapter(appointmentsList)
        binding.rvListAppointmentsPatient.layoutManager = LinearLayoutManager(this)
        binding.rvListAppointmentsPatient.adapter = adapter
    }

    private fun getAppointmentByPatientId(id:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getAppointmentByPatientId(id)
            val appointments = call.body()
            runOnUiThread{
                if(call.isSuccessful) {
                    appointmentsList.clear()
                    appointmentsList.addAll(appointments!!)
                    adapter.notifyDataSetChanged()
                }else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(this, "Error gaaa", Toast.LENGTH_SHORT).show()
    }
}