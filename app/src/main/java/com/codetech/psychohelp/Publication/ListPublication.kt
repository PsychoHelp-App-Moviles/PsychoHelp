package com.codetech.psychohelp.Publication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codetech.psychohelp.APIService
import com.codetech.psychohelp.R
import com.codetech.psychohelp.databinding.ActivityListPublicationBinding
import com.codetech.psychohelp.fragments.HomePsychoFragment
import com.codetech.psychohelp.patient.PatientAdapter
import com.codetech.psychohelp.patient.PatientViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListPublication : Fragment() {

    private var adapterPublication: RecyclerView.Adapter<PublicationViewHolder> ?= null
    private val publicationList = mutableListOf<PublicationsResponse>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_list_publication, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        getPublicationByPsychologistId()
        view?.findViewById<RecyclerView>(R.id.rvListPublications).apply{
            adapterPublication = PublicationAdapter(publicationList)
            this?.layoutManager = LinearLayoutManager(activity)
            this?.adapter = adapterPublication
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://psychohelp.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    private fun getPublicationByPsychologistId() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getPublications()
            val publications = call.body()
            activity?.runOnUiThread{
                if(call.isSuccessful) {
                    publicationList.clear()
                    publicationList.addAll(publications!!)
                    adapterPublication?.notifyDataSetChanged()
                }else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(context, "An Error Ocurred", Toast.LENGTH_SHORT).show()
    }

    fun create(view: View) {
        val publicationCreated = PublicationDto("string", "hola", "triuste", "asdajsdnhajkds", "asdasd", "1")
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).createPublication("1", publicationCreated)
            val publications = call.body()
            activity?.runOnUiThread{
                if(call.isSuccessful) {
                    Log.d("Main", call.body().toString())
                }else {
                    showError()
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomePsychoFragment()
    }
}