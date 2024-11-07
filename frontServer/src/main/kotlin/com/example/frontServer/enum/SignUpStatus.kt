package com.example.frontServer.enum

enum class SignUpStatus(val message: String) {
    SUCCESS("Sign Success"),

    DUPLICATED("Id or Email Duplicated"),
}