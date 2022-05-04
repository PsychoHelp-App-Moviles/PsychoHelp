package com.codetech.psychohelp.patient

import com.google.gson.annotations.SerializedName

data class PatientDto(@SerializedName("firstName") val firstName: String,
                      @SerializedName("lastName") val lastName: String,
                      @SerializedName("email") val email: String,
                      @SerializedName("phone") val phone: String,
                      @SerializedName("password") val password: String,
                      @SerializedName("date") val date: String,
                      @SerializedName("gender") val gender: String,
                      @SerializedName("img") val img: String)
