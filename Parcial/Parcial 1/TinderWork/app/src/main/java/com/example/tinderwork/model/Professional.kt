package com.example.tinderwork.model
import java.io.Serializable

data class Professional (
    val pName: String,
    val pProfession: String,
    val pExperience: String,
    val pImage: Int,
    val pMail: String,
    val pPhone: String,
    val pAbout: String
) : Serializable