package com.totest.commons

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.Scanner

val BASE_URL = "http://" + getEnv("APP_HOST", "localhost") + ":8080"
val HEALTH = "${BASE_URL}/health"

const val UUID_REGEX = "([a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}){1}"
const val HTTP_OK : Int = 200
const val HTTP_CREATED : Int = 201
const val HTTP_BAD_REQUEST : Int = 400


fun getEnv(name: String, defaultValue: String) = System.getenv(name) ?: defaultValue
fun toJson(src: Any?): String = Gson().toJson(src)

fun waitForResource(url: String) {
    var attempts = 0
    repeat(1000) {
        attempts++
        if (attempts == 30) {
            throw RuntimeException("Service not found! -> $url")
        }

        val response = OkHttpClient()
                .newCall(Request
                        .Builder()
                        .url(url)
                        .build())
                .execute()

        if (200 == response.code()) {
            return
        }
    }
}

open class ReadFile {
    companion object {
        fun readAndReplace(folder: String, name: String, stringToReplace : Map<String, String>) : String {
            var file = readFileAsString("/__files$folder/$name.json")
            stringToReplace.forEach{ k, v ->
                file = file.replace(oldValue = k, newValue = v)
            }
            return file
        }

        fun read(folder: String, name: String) =
            readFileAsString("/__files$folder/$name.json")

        fun readFileAsString(path: String): String {
            val inputStream = this::class.java.getResourceAsStream(path)

            val scanner = Scanner(inputStream, "UTF-8")
            val contentFile = scanner.useDelimiter("\\Z").next()
            scanner.close()

            return contentFile
        }
    }
}
