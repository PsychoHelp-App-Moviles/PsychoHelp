package com.codetech.psychohelp.psychologist

import com.codetech.psychohelp.*
import com.codetech.psychohelp.patient.PatientsResponse

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
import androidx.appcompat.app.AppCompatActivity
import com.codetech.psychohelp.patient.PatientsLogin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PsychoLogin : AppCompatActivity() {

    private var psychologistResponse: PsychologistResponse?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_psycho_login)

        findViewById<TextView>(R.id.tvYouArePactent).setOnClickListener{
            val intent = Intent(this, PatientsLogin::class.java)
            startActivity(intent)
        }
    }



    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://psychohelp.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getPsychologist(email: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getPsychologistByEmail(email)
            val patient = call.body()
            runOnUiThread{
                if(call.isSuccessful) {
                    psychologistResponse = patient!!
                    Log.d("Main", psychologistResponse!!.name)
                }else {
                    showError()
                }
            }
        }
    }

    fun validateLoginPsycho(view: View) {
        val email = findViewById<EditText>(R.id.etEmailLoginPsycho).text.toString()
        val password = findViewById<EditText>(R.id.etPassLoginPsycho).text.toString()
        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }else {
            getPsychologist(email)
            val psychoSet = psychologistResponse
            if(psychoSet != null) {
                Log.d("Main", psychoSet!!.id)
                if (psychoSet.password == password) {
                    val intent = Intent(this, PsychologistActivity::class.java)
                    intent.putExtra("patient", psychoSet.id)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this, "No psychologist with this email", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun goToPatient(view: View) {
        val intent = Intent(this, PatientsLogin::class.java)
        startActivity(intent)
    }

    private fun showError() {
        Toast.makeText(this, "An error has been occurred while processing your request", Toast.LENGTH_SHORT).show()
    }

}