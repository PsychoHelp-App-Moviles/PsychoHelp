package com.codetech.psychohelp.psychologist

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
import com.codetech.psychohelp.APIService
import com.codetech.psychohelp.R
import com.codetech.psychohelp.fragments.PsychologistsFragment
import com.codetech.psychohelp.patient.PatientDto
import com.codetech.psychohelp.patient.PatientsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PsychoProfile : Fragment() {
    private lateinit var psychologistResponse: PsychologistResponse

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.psycho_profile, container, false)
    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        view?.findViewById<Button>(R.id.edit_psycho_profile_button)?.setOnClickListener(View.OnClickListener { v ->
            openDialogProfile(v)
        })
        getPsychologist("1")
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://psychohelp.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getPsychologist(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getPsychologistById(id)
            val psycho = call.body()
            activity?.runOnUiThread{
                if(call.isSuccessful) {
                    psychologistResponse = psycho!!
                    Log.d("Main", psychologistResponse.name)
                    retrievePsycho(psychologistResponse)
                }else {
                    showError()
                }
            }
        }
    }

    private fun retrievePsycho(psychologistResponse: PsychologistResponse) {
        view?.findViewById<TextView>(R.id.etPsychoName)?.setText(psychologistResponse.name)
        view?.findViewById<TextView>(R.id.etPsychoEmail)?.setText(psychologistResponse.email)
        view?.findViewById<TextView>(R.id.etPsychoBirthday)?.setText(psychologistResponse.birthdayDate)
        view?.findViewById<TextView>(R.id.etPsychoPhone)?.setText(psychologistResponse.phone)
        view?.findViewById<TextView>(R.id.etPsychoFormation)?.setText(psychologistResponse.formation)
        view?.findViewById<TextView>(R.id.etPsychoAboutMe)?.setText(psychologistResponse.about)
    }

    private fun openDialogProfile(view: View) {
        val builder = AlertDialog.Builder(view.context)
        val view2 = layoutInflater.inflate(R.layout.psycho_profile_dialog, null)
        builder.setView(view2)
        val dialog = builder.create()
        dialog.show()
        //Edit motive and history attributes
        view2.findViewById<EditText>(R.id.etName).setText(psychologistResponse.name)
        view2.findViewById<EditText>(R.id.etEmail).setText(psychologistResponse.email)
        view2.findViewById<EditText>(R.id.etDate).setText(psychologistResponse.birthdayDate)
        view2.findViewById<EditText>(R.id.etPhone).setText(psychologistResponse.phone)
        view2.findViewById<EditText>(R.id.etFormation).setText(psychologistResponse.formation)
        view2.findViewById<EditText>(R.id.etAboutMe).setText(psychologistResponse.about)

        //Call api to update appointment
        view2.findViewById<Button>(R.id.btPsychoSave).setOnClickListener {
            val name = view2.findViewById<EditText>(R.id.etName).text.toString()
            val email = view2.findViewById<EditText>(R.id.etEmail).text.toString()
            val date = view2.findViewById<EditText>(R.id.etDate).text.toString()
            val phone = view2.findViewById<EditText>(R.id.etPhone).text.toString()
            val formation = view2.findViewById<EditText>(R.id.etFormation).text.toString()
            val about = view2.findViewById<EditText>(R.id.etAboutMe).text.toString()
            val psychoUpdate = PsychologistDto(
                name,
                psychologistResponse.dni,
                date,
                email,
                psychologistResponse.password,
                phone,
                psychologistResponse.specialization,
                formation,
                about,
                psychologistResponse.genre,
                psychologistResponse.sessionType,
                psychologistResponse.img,
                psychologistResponse.cmp,
                psychologistResponse.active,
                psychologistResponse.fresh
            )
            // Call to api
            CoroutineScope(Dispatchers.IO).launch {
                val call = getRetrofit().create(APIService::class.java)
                    .updatePsychologist(psychologistResponse.id, psychoUpdate)
                val psycho = call.body()
                activity?.runOnUiThread {
                    if (call.isSuccessful) {
                        psychologistResponse = psycho!!
                        retrievePsycho(psychologistResponse)
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