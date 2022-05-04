package com.codetech.psychohelp.psychologist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.text.bold
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetech.psychohelp.APIService
import com.codetech.psychohelp.ID
import com.codetech.psychohelp.patient.PatientsResponse
import com.codetech.psychohelp.R
import com.codetech.psychohelp.appointment.AppointmentAdapter
import com.codetech.psychohelp.appointment.AppointmentClickListener
import com.codetech.psychohelp.appointment.AppointmentDto
import com.codetech.psychohelp.appointment.AppointmentsResponse
import com.codetech.psychohelp.databinding.ActivityPsychologistLogbookBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PsychologistLogbook : AppCompatActivity(), AppointmentClickListener {

    private lateinit var patientResponse : PatientsResponse
    private lateinit var appointmentResponse : AppointmentsResponse
    private val appointmentList = mutableListOf<AppointmentsResponse>()
    private lateinit var  binding: ActivityPsychologistLogbookBinding
    private lateinit var adapter : AppointmentAdapter
    private var appointmentSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPsychologistLogbookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra(ID)
        Log.d("id", id!!)
        getPatient("1")
        getAppointments("1")
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
                    patientResponse = patient!!
                    Log.d("Main", patientResponse.firstName)
                    retrievePatientInformation(patientResponse)
                }else {
                    showError()
                }
            }
        }
    }

    private fun retrievePatientInformation(patientResponse: PatientsResponse) {
        val name = SpannableStringBuilder() .bold { append("Nombre Completo: ") }.append(patientResponse.firstName + " " + patientResponse.lastName)
        val dateOfBirth = SpannableStringBuilder() .bold { append("Fecha de Nacimiento: ")} .append(patientResponse.date)
        val gender = SpannableStringBuilder().bold { append("Género: ") }.append(patientResponse.gender)
        val email = SpannableStringBuilder().bold { append("Correo electrónico: ") }.append(patientResponse.email)
        val phone = SpannableStringBuilder().bold { append("Número de teléfono: ") }.append(patientResponse.phone)
        findViewById<TextView>(R.id.tvName).apply { text = name }
        findViewById<TextView>(R.id.tvDateOfBirth).apply { text = dateOfBirth }
        findViewById<TextView>(R.id.tvGender).apply { text = gender }
        findViewById<TextView>(R.id.tvEmail).apply { text = email }
        findViewById<TextView>(R.id.tvPhoneNumber).apply { text = phone }
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
                    appointmentResponse = appointment!!
                    retrieveAppointment(appointmentResponse)
                }else {
                    showError()
                }
            }
        }
    }

    private fun retrieveAppointment(appointment: AppointmentsResponse) {
        val session = "Session: " + appointment.scheduleDate
        findViewById<TextView>(R.id.tvPlaceHolderSession).apply { text = session }
        findViewById<TextView>(R.id.tvMotive).apply { text = appointment.motive }
        findViewById<TextView>(R.id.tvHistory).apply { text = appointment.personalHistory }
    }

    private fun showError() {
        Toast.makeText(this, "An error has been occurred while processing your request", Toast.LENGTH_SHORT).show()
    }

    fun openDialog(view: View) {
        if(appointmentSelected) {
            val builder = AlertDialog.Builder(this)
            val view2 = layoutInflater.inflate(R.layout.edit_logbook_dialog, null)
            builder.setView(view2)
            val dialog = builder.create()
            dialog.show()
            //Edit motive and history attributes
            view2.findViewById<EditText>(R.id.etMotive).setText(appointmentResponse.motive)
            view2.findViewById<EditText>(R.id.etHistory)
                .setText(appointmentResponse.personalHistory)

            //Call api to update appointment
            view2.findViewById<Button>(R.id.btDialogEdit).setOnClickListener {
                val motive = view2.findViewById<EditText>(R.id.etMotive).text.toString()
                val history = view2.findViewById<EditText>(R.id.etHistory).text.toString()
                val appointmentUpdated = AppointmentDto(
                    appointmentResponse.meetUrl,
                    motive,
                    appointmentResponse.treatment,
                    appointmentResponse.scheduleDate,
                    appointmentResponse.testRealized,
                    history,
                    appointmentResponse.psychologistId,
                    appointmentResponse.patientId
                )
                // Call to api
                CoroutineScope(Dispatchers.IO).launch {
                    val call = getRetrofit().create(APIService::class.java)
                        .updateAppointment(appointmentResponse.id, appointmentUpdated)
                    val appointment = call.body()
                    runOnUiThread {
                        if (call.isSuccessful) {
                            appointmentResponse = appointment!!
                            retrieveAppointment(appointmentResponse)
                            dialog.dismiss()
                        } else {
                            showError()
                        }
                    }
                }
                dialog.hide()
            }
        }else{
            Toast.makeText(this, "No appointment selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(appointment: AppointmentsResponse) {
        Log.d("Main", appointment.id)
        getAppointment(appointment.id)
        appointmentSelected = true
    }
}