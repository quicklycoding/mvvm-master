package com.quicklycoding.mvvm_master.repository

import com.quicklycoding.mvvm_master.util.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

fun Int.isUnauthorized(): Boolean = this == 401
fun Int.isBadRequest(): Boolean = this == 400
fun Int.isCreated(): Boolean = this == 201
fun Int.isNotFound(): Boolean = this == 404
fun Int.isOk(): Boolean = this == 200

abstract class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        when {
            response.isSuccessful -> return response.body()!!
            else -> {
                val errorResponse = response.errorBody()!!.charStream().readText()

                var message = ""
                try {
                    val jsonObj = JSONObject(errorResponse)

                    val errorMsg = jsonObj.getString("message")
                    val errorCode = response.code()

                    message = "Error code: $errorCode\nError message: $errorMsg"

                } catch (ex: JSONException) {
                }
                throw ApiException(message)
            }
        }
    }

}