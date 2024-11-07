package com.example.frontServer.dto

import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class CustomMultipartFile(
    private val file: File
): MultipartFile {
    override fun getInputStream(): InputStream {
        return FileInputStream(file)
    }

    override fun getName(): String {
        return file.name
    }

    override fun getOriginalFilename(): String? {
        return file.name
    }

    override fun getContentType(): String? {
        return "application/octet-stream" // 필요 시 실제 MIME 타입으로 변경
    }

    override fun isEmpty(): Boolean {
        return file.length() == 0L
    }

    override fun getSize(): Long {
        return file.length()
    }

    override fun getBytes(): ByteArray {
        return file.readBytes()
    }

    override fun transferTo(dest: File) {
        file.copyTo(dest, overwrite = true)
    }
}