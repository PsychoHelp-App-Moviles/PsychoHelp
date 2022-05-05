package com.codetech.psychohelp

import com.codetech.psychohelp.Publication.PublicationDto
import com.codetech.psychohelp.Publication.PublicationsResponse
import com.codetech.psychohelp.appointment.AppointmentDto
import com.codetech.psychohelp.appointment.AppointmentsResponse
import com.codetech.psychohelp.patient.PatientDto
import com.codetech.psychohelp.patient.PatientsResponse
import com.codetech.psychohelp.psychologist.PsychologistDto
import com.codetech.psychohelp.psychologist.PsychologistResponse
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

    @GET("patients/email/{patientEmail}")
    suspend fun getPatientByEmail(@Path("patientEmail") patientEmail:String): Response<PatientsResponse>

    @GET("psychologists/{psychologistId}")
    suspend fun getPsychologistById(@Path("psychologistId") psychologistId:String): Response<PsychologistResponse>

    @GET("psychologists/email/{psychologistEmail}")
    suspend fun getPsychologistByEmail(@Path("psychologistEmail") psychologistEmail:String): Response<PsychologistResponse>

    @GET("appointment/{appointmentId}")
    suspend fun getAppointmentById(@Path("appointmentId") appointmentId:String): Response<AppointmentsResponse>

    @PUT("appointment/{appointmentId}")
    suspend fun updateAppointment(@Path("appointmentId") appointmentId: String, @Body appointmentDto: AppointmentDto): Response<AppointmentsResponse>

    @PUT("patients/{patientId}")
    suspend fun updatePatient(@Path("patientId") patientId: String, @Body patientDto: PatientDto): Response<PatientsResponse>

    @PUT("psychologists/{psychologistId}")
    suspend fun updatePsychologist(@Path("psychologistId") psychologistId: String, @Body psychologistDto: PsychologistDto): Response<PsychologistResponse>

    @GET("appointment/patient/{id}")
    suspend fun getAppointmentByPatientId(@Path("id") id:String):Response<List<AppointmentsResponse>>

    @GET("appointment/psychologist/{id}")
    suspend fun getAppointmentByPsychologistId(@Path("id") id:String):Response<List<AppointmentsResponse>>

    @GET("publications/psychologist/{id}")
    suspend fun getPublicationByPsychologist(@Path("id") id:String):Response<List<PublicationsResponse>>

    @GET("publications")
    suspend fun getPublications() :Response<List<PublicationsResponse>>

    @POST("publications/psychologists/{id}")
    suspend fun createPublication(@Path("id") id:String, @Body publicationDto: PublicationDto):Response<PublicationsResponse>


}