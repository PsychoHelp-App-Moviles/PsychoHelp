package com.codetech.psychohelp

import com.codetech.psychohelp.appointment.AppointmentsResponse
import com.codetech.psychohelp.patient.PatientsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("appointment/psychologist/{id}/patient")
    suspend fun  getPatientsByPsychologistId(@Path("id") id:String): Response<List<PatientsResponse>>

    @GET("appointment/psychologist/{psychologistId}/patient/{patientId}")
    suspend fun getAllAppointmentsByPatientIdAndPsychologistId(@Path("psychologistId") psychologistId:String, @Path("patientId") patientId:String): Response<List<AppointmentsResponse>>

    @GET("patients/{patientId}")
    suspend fun getPatientById(@Path("patientId") patientId:String): Response<PatientsResponse>

    @GET("appointments/{appointmentId}")
    suspend fun getAppointmentById(@Path("appointmentId") appointmentId:String): Response<AppointmentsResponse>
}