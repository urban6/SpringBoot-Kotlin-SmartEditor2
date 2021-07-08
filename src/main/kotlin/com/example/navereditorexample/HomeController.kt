package com.example.navereditorexample

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.*
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.collections.HashMap


@Controller
class HomeController {

    @GetMapping(path = ["/"])
    fun index(): String {
        return "write"
    }

    @Deprecated(message = "HTML5 이후로는 멀티 이미지 업로드 팝업이 떠서, 아래의 기능은 동작하지 않음")
    @RequestMapping(path = ["/singleImageUpload"])
    fun singleImageUpload(
        request: HttpServletRequest,
        imageFile: ImageFile
    ): String {
        val callback = imageFile.callback
        val callbackFunc = imageFile.callbackFunc
        var fileResult = ""
        var result = ""
        val multiFile: MultipartFile = imageFile.fileData

        if (multiFile != null && multiFile.size > 0 && !multiFile.isEmpty) {
            if (multiFile.contentType!!.toLowerCase().startsWith("image/")) {
                val oriName = multiFile.name
                val uploadPath = request.servletContext.getRealPath("/img")
                val path = uploadPath + "/editor/"
                val file = File(path)
                if (!file.exists()) {
                    file.mkdirs()
                }
                val fileName = UUID.randomUUID().toString()
                imageFile.fileData.transferTo(File(path + fileName))
                fileResult += "&bNewLine=true&sFileName=$oriName&sFileURL=/img/editor/$fileName"
            } else {
                fileResult += "&errstr=error"
            }
        } else {
            fileResult += "&errstr=error"
        }

        result = "redirect:" + callback + "?callback_func=" + URLEncoder.encode(callbackFunc, "UTF-8") + fileResult
        return result
    }

    /**
     * 다중 이미지 업로드
     */
    @RequestMapping(path = ["/multiImageUpload"])
    fun multiImageUpload(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @Value("\${image.upload.path}") filePath: String
    ) {
        // 파일 확장자를 소문자로 변경
        val fileName = request.getHeader("file-name")
        var fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1)
        fileExtension = fileExtension.toLowerCase()

        val file = File("$filePath/")
        if (!file.exists()) {
            file.mkdirs()
        }

        // 파일 이름 설정
        val formatter = SimpleDateFormat("yyyyMMddHHmmss")
        val today = formatter.format(Date())
        val realFileName = today + UUID.randomUUID().toString() + "." + fileExtension
        val realFilePathName = "$filePath/$realFileName"

        ///////////////// 서버에 파일쓰기 /////////////////
        val inputStream: InputStream? = request.inputStream
        val os: OutputStream = FileOutputStream(realFilePathName)
        var numRead: Int
        val b = ByteArray(request.getHeader("file-size").toInt())
        while (inputStream!!.read(b, 0, b.size).also { numRead = it } != -1) {
            os.write(b, 0, numRead)
        }
        inputStream.close()
        os.flush()
        os.close()
        ///////////////// 서버에 파일쓰기 /////////////////

        // 정보 출력
        var saveFileInfo = ""
        saveFileInfo += "&bNewLine=true"
        // img 태그의 title 속성을 원본파일명으로 적용시켜주기 위함
        saveFileInfo += "&sFileName=$fileName"
        saveFileInfo += "&sFileURL=/static/img/editor/$realFileName"
        val print: PrintWriter = response.writer
        print.print(saveFileInfo)
        print.flush()
        print.close()
    }

    @ResponseBody
    @PostMapping(path = ["/editor/save"])
    fun saveEditor(
        request: HttpServletRequest
    ): String {
        println("PARAM :: ${request.getParameter("content")}")
        return ""
    }
}