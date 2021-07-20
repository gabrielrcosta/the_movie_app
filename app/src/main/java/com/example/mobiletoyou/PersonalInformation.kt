package com.example.mobiletoyou

import com.google.gson.annotations.SerializedName

class PersonalInformation(
    @SerializedName("biography")
    val biography: String,
    @SerializedName("profile_path")
    val profilePath: String,
    @SerializedName("place_of_birth")
    val placeOfBirth: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("name")
    val personName: String,
    @SerializedName("known_for_department")
    val career: String,
    @SerializedName("popularity")
    val popularity: String
)