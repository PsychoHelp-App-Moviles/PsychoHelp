package com.codetech.psychohelp

import com.codetech.psychohelp.Publication.PublicationDto
import com.codetech.psychohelp.Publication.PublicationsResponse
import com.codetech.psychohelp.appointment.AppointmentsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {
    @GET("appointment/patient/{id}")
    suspend fun getAppointmentByPatientId(@Path("id") id:String):Response<List<AppointmentsResponse>>

    @GET("publications/psychologist/{id}")
    suspend fun getPublicationByPsychologist(@Path("id") id:String):Response<List<PublicationsResponse>>

    @POST("publications/psychologists/{id}")
    suspend fun createPublication(@Path("id") id:String, @Body publicationDto: PublicationDto):Response<PublicationsResponse>


}