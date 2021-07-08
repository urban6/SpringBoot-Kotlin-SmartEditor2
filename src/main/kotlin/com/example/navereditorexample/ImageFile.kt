package com.example.navereditorexample

import org.springframework.web.multipart.MultipartFile

data class ImageFile(
    val fileData: MultipartFile,
    val callback: String,
    val callbackFunc: String
)