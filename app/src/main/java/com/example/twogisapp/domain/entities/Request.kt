package com.example.twogisapp.domain.entities

import java.io.Serializable

data class Request (
    val category: Int,
    val from: String,
    val to: String,
    val date: String,
    val comment: String
) : Serializable