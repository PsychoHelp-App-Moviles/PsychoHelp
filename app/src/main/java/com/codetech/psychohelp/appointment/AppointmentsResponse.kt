package com.codetech.psychohelp.appointment

import com.google.gson.annotations.SerializedName
data class AppointmentsResponse(@SerializedName("id") val id: String,
                                @SerializedName("meetUrl") val meetUrl: String,
                                @SerializedName("motive") val motive: String,
                                @SerializedName("treatment") val treatment: String,
                                @SerializedName("scheduleDate") val scheduleDate: String,
                                @SerializedName("testRealized") val testRealized: String,
                                @SerializedName("personalHistory") val personalHistory: String,
                                @SerializedName("psychologistId") val psychologistId: String,
                                @SerializedName("patientId") val patientId: String)
