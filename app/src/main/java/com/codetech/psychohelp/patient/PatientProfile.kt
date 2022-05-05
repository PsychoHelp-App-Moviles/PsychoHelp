package com.codetech.psychohelp.patient

import androidx.appcompat.app.AppCompatActivity
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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codetech.psychohelp.APIService
import com.codetech.psychohelp.R
import com.codetech.psychohelp.appointment.AppointmentDto
import com.codetech.psychohelp.appointment.AppointmentsResponse
import com.codetech.psychohelp.fragments.PsychologistsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PatientProfile : Fragment() {

    private lateinit var patientResponse : PatientsResponse

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.patient_profile, container, false)
    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        view?.findViewById<Button>(R.id.edit_patient_profile_button)?.setOnClickListener(View.OnClickListener { v ->
            openDialogProfile(v)
        })
        getPatient("1")
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://psychohelp.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getPatient(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getPatientById(id)
            val patient = call.body()
            activity?.runOnUiThread{
                if(call.isSuccessful) {
                    patientResponse = patient!!
                    Log.d("Main", patientResponse.firstName)
                    retrievePatient(patientResponse)
                }else {
                    showError()
                }
            }
        }
    }

    private fun retrievePatient(patientsResponse: PatientsResponse) {
        view?.findViewById<TextView>(R.id.textview_name)?.setText(patientsResponse.firstName + " " + patientsResponse.lastName)
        view?.findViewById<TextView>(R.id.textview_email)?.setText(patientsResponse.email)
        view?.findViewById<TextView>(R.id.textview_birthdate)?.setText(patientsResponse.date)
        view?.findViewById<TextView>(R.id.textview_phone)?.setText(patientsResponse.phone)
    }

    fun openDialogProfile(view: View) {
        val builder = AlertDialog.Builder(view.context)
        val view2 = layoutInflater.inflate(R.layout.patient_profile_dialog, null)
        builder.setView(view2)
        val dialog = builder.create()
        dialog.show()
        //Edit motive and history attributes
        val completeName = patientResponse.firstName + " " + patientResponse.lastName
        view2.findViewById<EditText>(R.id.name_text_input).setText(completeName)
        view2.findViewById<EditText>(R.id.email_text_input).setText(patientResponse.email)
        view2.findViewById<EditText>(R.id.date_text_input).setText(patientResponse.date)
        view2.findViewById<EditText>(R.id.phone_text_input).setText(patientResponse.phone)

        //Call api to update appointment
        view2.findViewById<Button>(R.id.edit_patient_profile_button).setOnClickListener {
            val email = view2.findViewById<EditText>(R.id.email_text_input).text.toString()
            val date = view2.findViewById<EditText>(R.id.date_text_input).text.toString()
            val phone = view2.findViewById<EditText>(R.id.phone_text_input).text.toString()
            val patientUpdate = PatientDto(
                patientResponse.firstName,
                patientResponse.lastName,
                email,
                phone,
                patientResponse.password,
                date,
                patientResponse.gender,
                patientResponse.img
            )
            // Call to api
            CoroutineScope(Dispatchers.IO).launch {
                val call = getRetrofit().create(APIService::class.java)
                    .updatePatient(patientResponse.id, patientUpdate)
                val patient = call.body()
                activity?.runOnUiThread {
                    if (call.isSuccessful) {
                        patientResponse = patient!!
                        retrievePatient(patientResponse)
                        dialog.dismiss()
                    } else {
                        showError()
                    }
                }
            }
            dialog.hide()
        }
    }


    private fun showError() {
        Toast.makeText(context, "An error has been occurred while processing your request", Toast.LENGTH_SHORT).show()
    }


    companion object {

        @JvmStatic
        fun newInstance() = PsychologistsFragment()
    }


}