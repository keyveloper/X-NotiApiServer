package com.example.frontServer.exception

import com.example.frontServer.enum.FrontServerError

class FileNotExistException: ApplicationException(FrontServerError.FILE_NOT_EXIST.ordinal, "Can't send File: File Not Found")