package com.codetech.psychohelp.Publication

import com.google.gson.annotations.SerializedName

data class PublicationDto(@SerializedName("title") var title: String,
                                @SerializedName("description") var description: String,
                                @SerializedName("tags") var tags: String,
                                @SerializedName("photoUrl") var photoUrl: String,
                                @SerializedName("content") var content: String,
                                @SerializedName("psychologistId") var psychologistId: String) {
}
