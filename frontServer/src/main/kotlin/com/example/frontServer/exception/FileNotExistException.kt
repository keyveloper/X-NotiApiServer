package com.example.frontServer.exception

import com.example.frontServer.enum.ErrorCode

class FileNotExistException: ApplicationException(ErrorCode.FILE_NOT_EXIST.ordinal, "Can't send File: File Not Found")