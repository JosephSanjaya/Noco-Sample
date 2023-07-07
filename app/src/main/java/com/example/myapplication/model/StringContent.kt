package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class StringContent(
    val Id: Int,
    val key: String,
    val platform: String,

    @SerializedName("lang-id")
    val langId: String,

    @SerializedName("lang-en")
    val langEn: String,

    @SerializedName("lang-th")
    val langTh: String,

    @SerializedName("lang-vi")
    val langVi: String,

    @SerializedName("lang-my")
    val langMy: String,
)

