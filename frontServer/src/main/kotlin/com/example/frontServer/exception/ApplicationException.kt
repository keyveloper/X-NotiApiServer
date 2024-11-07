package com.example.frontServer.exception

open class ApplicationException(val errorCode: Int, message: String): RuntimeException(message) {
}