package com.codetech.psychohelp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import com.codetech.psychohelp.Publication.ListPublication
import android.view.View
import androidx.appcompat.app.AlertDialog
import android.widget.Button

const val EXTRA_MESSAGE = "com.codetech.psychohelp.MESSAGE"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navBtnPatient = findViewById<Button>(R.id.btnPatient)
        val navBtnPsychologist = findViewById<Button>(R.id.btnPsychologist)

        navBtnPatient.setOnClickListener {
            val intent = Intent(this, PatientActivity::class.java)
            startActivity(intent)
        }
        navBtnPsychologist.setOnClickListener {
            val intentPsycho = Intent(this, PsychologistActivity::class.java)
            startActivity(intentPsycho)
        }
    }
    fun sendMessage(view: View){
        val message = "1"
        val intent = Intent(this, HistoryAppointmentsPsychologist::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    fun sendPublication(view: View){
        val message = "1"
        val intent = Intent(this, ListPublication::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    fun sendMessage(view: View){
        val message = "1"
        val intent = Intent(this, ListOfPatients::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }
}