package com.codetech.psychohelp

import com.codetech.psychohelp.appointment.AppointmentDto
import com.codetech.psychohelp.appointment.AppointmentsResponse
import com.codetech.psychohelp.patient.PatientDto
import com.codetech.psychohelp.patient.PatientsResponse
import retrofit2.Response
import retrofit2.http.*

interface APIService {
    @GET("appointment/psychologist/{id}/patient")
    suspend fun  getPatientsByPsychologistId(@Path("id") id:String): Response<List<PatientsResponse>>

    @GET("appointment/psychologist/{psychologistId}/patient/{patientId}")
    suspend fun getAllAppointmentsByPatientIdAndPsychologistId(@Path("psychologistId") psychologistId:String,
                                                               @Path("patientId") patientId:String): Response<List<AppointmentsResponse>>

    @GET("patients/{patientId}")
    suspend fun getPatientById(@Path("patientId") patientId:String): Response<PatientsResponse>

    @GET("appointment/{appointmentId}")
    suspend fun getAppointmentById(@Path("appointmentId") appointmentId:String): Response<AppointmentsResponse>

    @PUT("appointment/{appointmentId}")
    suspend fun updateAppointment(@Path("appointmentId") appointmentId: String, @Body appointmentDto: AppointmentDto): Response<AppointmentsResponse>
}