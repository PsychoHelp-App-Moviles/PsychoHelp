package com.codetech.psychohelp.appointment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codetech.psychohelp.APIService
import com.codetech.psychohelp.R
import com.codetech.psychohelp.fragments.DatesFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistoryAppointmentsPatient : Fragment() {
    private var appointmentAdapter: RecyclerView.Adapter<AppointmentViewHolder> ?= null
    private val appointmentsList = mutableListOf<AppointmentsResponse>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_history_appointments_patient, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        getAppointmentByPatientId("1")
        view?.findViewById<RecyclerView>(R.id.rvListAppointmentsPatient).apply {
            appointmentAdapter = AppointmentAdapter(appointmentsList)
            this?.layoutManager = LinearLayoutManager(activity)
            this?.adapter = appointmentAdapter

        }
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://psychohelp.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getAppointmentByPatientId(id:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getAppointmentByPatientId(id)
            val appointments = call.body()
            activity?.runOnUiThread{
                if(call.isSuccessful) {
                    appointmentsList.clear()
                    appointmentsList.addAll(appointments!!)
                    appointmentAdapter?.notifyDataSetChanged()
                }else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(context, "An Error occurred", Toast.LENGTH_SHORT).show()
    }

    companion object {

        @JvmStatic
        fun newInstance() = DatesFragment()
    }
}