package com.codetech.psychohelp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

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
}