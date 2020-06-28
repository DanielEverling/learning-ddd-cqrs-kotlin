package com.totest.commons

import com.google.gson.Gson
import okhttp3.*
import kotlin.reflect.KClass


object Gateway {

    fun post(path: String, body: String) : Pair<Int, String> {
        val response: Response = OkHttpClient()
                .newCall(Request.Builder()
                        .url("$BASE_URL/$path")
                        .post(RequestBody
                                .create(MediaType.get("application/json"), body)
                        )
                        .headers(addHeaders())
                        .build())
                .execute()

        val location = response.header("location")!!
        return Pair(response.code(), location)
    }

    fun <T : Any> post(path: String, body: String, elementClass: KClass<T>) : Pair<Int, T> {
        val response: Response = OkHttpClient()
                .newCall(Request.Builder()
                        .url("$BASE_URL/$path")
                        .post(RequestBody
                                .create(MediaType.get("application/json"), body)
                        )
                        .headers(addHeaders())
                        .build())
                .execute()

        return Pair(response.code(), Gson().fromJson(response.body()!!.string(), elementClass.java))
    }

    fun <T : Any> patch(path: String, body: String, elementClass: KClass<T>) : Pair<Int, T> {
        val response: Response = OkHttpClient()
                .newCall(Request.Builder()
                        .url("$BASE_URL/$path")
                        .patch(RequestBody
                                .create(MediaType.get("application/json"), body)
                        )
                        .headers(addHeaders())
                        .build())
                .execute()

        return Pair(response.code(), Gson().fromJson(response.body()!!.string(), elementClass.java))
    }

    fun <T : Any> get(location: String, elementClass: KClass<T>): Pair<Int, T?> {
        val response= OkHttpClient()
                .newCall(Request
                        .Builder()
                        .url(location)
                        .headers(addHeaders())
                        .build())
                .execute()

        return Pair(response.code(), Gson().fromJson(response.body()!!.string(), elementClass.java))
    }

    fun <T : Any> get(path: String, queryString: String, elementClass: KClass<T>): Pair<Int, T?> {
        val response= OkHttpClient()
                .newCall(Request
                        .Builder()
                        .url("$BASE_URL/$path/$queryString")
                        .headers(addHeaders())
                        .build())
                .execute()

        return Pair(response.code(), Gson().fromJson(response.body()!!.string(), elementClass.java))
    }

    private fun addHeaders(): Headers {
        val headers = mapOf(
            "Accept" to "application/json",
            "Content-Type" to "application/json"
        )
        return Headers.of(headers)
    }
}
