package com.codetech.psychohelp.patient

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.codetech.psychohelp.APIService
import com.codetech.psychohelp.ID
import com.codetech.psychohelp.PatientActivity
import com.codetech.psychohelp.R
import com.codetech.psychohelp.databinding.ActivityPsychologistLogbookBinding
import com.codetech.psychohelp.fragments.PsychologistsFragment
import com.codetech.psychohelp.psychologist.PsychoLogin
import com.codetech.psychohelp.psychologist.PsychologistLogbook
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PatientsLogin : AppCompatActivity() {

    private var patientResponse: PatientsResponse ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients_login)
        findViewById<TextView>(R.id.tvYouArePsycho).setOnClickListener{
            val intent = Intent(this, PsychoLogin::class.java)
            startActivity(intent)
        }
    }



    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://psychohelp.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getPatient(email: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getPatientByEmail(email)
            val patient = call.body()
            runOnUiThread{
                if(call.isSuccessful) {
                    patientResponse = patient!!
                    Log.d("Main", patientResponse!!.firstName)
                }else {
                    showError()
                }
            }
        }
    }

    fun validateLogin(view: View) {
        val email = findViewById<EditText>(R.id.etEmailLoginPatient).text.toString()
        val password = findViewById<EditText>(R.id.etPassLoginPatient).text.toString()
        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }else {
            getPatient(email)
            val patientSet = patientResponse
            if(patientSet != null) {
                Log.d("Main", patientSet!!.id)
                if (patientSet.password == password) {
                    val intent = Intent(this, PatientActivity::class.java)
                    intent.putExtra("patient", patientSet.id)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this, "No patient with this email", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun showError() {
        Toast.makeText(this, "An error has been occurred while processing your request", Toast.LENGTH_SHORT).show()
    }

}