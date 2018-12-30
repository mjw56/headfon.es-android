package es.headfon.headfones;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtility {
    public static void makeRequest(String url, String access_token, final SearchSpotifyActivity.CallbackInterface callback) {
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer " + access_token)
                .build();

        OkHttpClient mOkHttpClient = new OkHttpClient();
        Call mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("onFailure", "Failed to fetch data: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONObject jsonObject = new JSONObject(response.body().string());
                    // Log.d("onResponse: success", jsonObject.toString(3));
                    callback.onDataFetch(jsonObject.toString(3));
                } catch (JSONException e) {
                    Log.d("onResponse: failure", "Failed to parse data: " + e);
                }
            }
        });
    }
}
