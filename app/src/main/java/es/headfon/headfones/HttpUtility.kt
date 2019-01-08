package es.headfon.headfones

import android.util.Log
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class HttpUtility {
    interface DataCallbackInterface {
        fun onDataFetch(result: JSONObject)
    }

    fun makeRequest(url: String, access_token: String, callback: DataCallbackInterface) {
        val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer $access_token")
                .build()

        val mOkHttpClient = OkHttpClient()
        val mCall = mOkHttpClient.newCall(request)

        mCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("onFailure", "Failed to fetch data: $e")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonObject = JSONObject(response.body()!!.string())
                    callback.onDataFetch(jsonObject)
                } catch (e: JSONException) {
                    Log.d("onResponse: failure", "Failed to parse data: $e")
                }

            }
        })
    }
}
