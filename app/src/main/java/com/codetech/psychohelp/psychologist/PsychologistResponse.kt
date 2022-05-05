package com.codetech.psychohelp.psychologist

import com.google.gson.annotations.SerializedName

data class PsychologistResponse(@SerializedName("id") val id: String,
                                @SerializedName("name") val name: String,
                                @SerializedName("dni") val dni: String,
                                @SerializedName("birthdayDate") val birthdayDate: String,
                                @SerializedName("email") val email: String,
                                @SerializedName("password") val password: String,
                                @SerializedName("phone") val phone: String,
                                @SerializedName("specialization") val specialization: String,
                                @SerializedName("formation") val formation: String,
                                @SerializedName("about") val about: String,
                                @SerializedName("genre") val genre: String,
                                @SerializedName("sessionType") val sessionType: String,
                                @SerializedName("image") val img: String,
                                @SerializedName("cmp") val cmp: String,
                                @SerializedName("active") val active: Boolean,
                                @SerializedName("fresh") val fresh: Boolean)

