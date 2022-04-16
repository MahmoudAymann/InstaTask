package com.instabug.task.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.instabug.task.domain.WordsUseCase
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

object NetworkHandler {

    fun call(api: String): NetworkResult<String> {
        var httpConn: HttpURLConnection? = null
        var isReader: InputStreamReader? = null
        var bufReader: BufferedReader? = null
        val readTextBuf = StringBuffer()
        return try {
            val url = URL(api)
            httpConn = (url.openConnection() as? HttpURLConnection)?.apply {
                requestMethod = "GET"
                connectTimeout = 10000
                readTimeout = 10000
            }
            val inputStream: InputStream? = httpConn?.inputStream
            isReader = InputStreamReader(inputStream)
            bufReader = BufferedReader(isReader)
            var line: String? = bufReader.readLine()
            while (line != null) {
                readTextBuf.append(line)
                line = bufReader.readLine()
            }
            closeConnection(bufReader, isReader, httpConn)
            NetworkResult.Success(readTextBuf.toString())
        } catch (ex: MalformedURLException) {
            Log.e(WordsUseCase.TAG, ex.message, ex)
            closeConnection(bufReader, isReader, httpConn)
            NetworkResult.Failure(ex.message.orEmpty())
        } catch (ex: IOException) {
            Log.e(WordsUseCase.TAG, ex.message, ex)
            closeConnection(bufReader, isReader, httpConn)
            NetworkResult.Failure(ex.message.orEmpty())
        }
    }

    private fun closeConnection(
        bufReader: BufferedReader?,
        isReader: InputStreamReader?,
        httpConn: HttpURLConnection?
    ) {
        try {
            bufReader?.close()
            isReader?.close()
            httpConn?.disconnect()
        } catch (ex: IOException) {
            Log.e(WordsUseCase.TAG, ex.message, ex)
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager?.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager?.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

}