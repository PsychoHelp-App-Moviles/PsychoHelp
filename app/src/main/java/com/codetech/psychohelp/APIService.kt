package com.codetech.psychohelp

import com.codetech.psychohelp.appointment.AppointmentsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("appointment/patient/{id}")
    suspend fun getAppointmentByPatientId(@Path("id") id:String):Response<List<AppointmentsResponse>>
}