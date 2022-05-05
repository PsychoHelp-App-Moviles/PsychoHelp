package com.codetech.psychohelp.Publication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetech.psychohelp.APIService
import com.codetech.psychohelp.databinding.ActivityListPublicationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListPublication : AppCompatActivity() {
    private lateinit var binding: ActivityListPublicationBinding;
    private lateinit var adapter: PublicationAdapter;
    private val publicationList = mutableListOf<PublicationsResponse>()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityListPublicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getPublicationByPsychologistId("1")
        initRecyclerView()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun initRecyclerView() {
        adapter = PublicationAdapter(publicationList)
        binding.rvListPublications.layoutManager = LinearLayoutManager(this)
        binding.rvListPublications.adapter = adapter
    }

    private fun getPublicationByPsychologistId(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getPublicationByPsychologist(id)
            val publications = call.body()
            runOnUiThread{
                if(call.isSuccessful) {
                    publicationList.clear()
                    publicationList.addAll(publications!!)
                    adapter.notifyDataSetChanged()
                }else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(this, "Error gaaa", Toast.LENGTH_SHORT).show()
    }

    fun create(view: View) {
        val publicationCreated = PublicationDto("string", "hola", "triuste", "asdajsdnhajkds", "asdasd", "1")
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).createPublication("1", publicationCreated)
            val publications = call.body()
            runOnUiThread{
                if(call.isSuccessful) {
                    Log.d("Main", call.body().toString())
                }else {
                    showError()
                }
            }
        }
    }
}