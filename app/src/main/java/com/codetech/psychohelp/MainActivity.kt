package com.codetech.psychohelp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import com.codetech.psychohelp.Publication.ListPublication

const val EXTRA_MESSAGE = "com.codetech.psychohelp.MESSAGE"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
}