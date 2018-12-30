package es.headfon.headfones;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class SearchSpotifyActivity extends AppCompatActivity {
    private String access_token;
    private Call mCall;
    private final OkHttpClient mOkHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_spotify);
        Intent intent = getIntent();
        access_token = intent.getExtras().getString("access_token");
    }

    public void onSearchClick(View view) {
        view.clearFocus();
        EditText search  = (EditText)findViewById(R.id.searchText);

        String url = "https://api.spotify.com/v1/search/?q=" + search.getText().toString() + "*&type=album,artist,playlist,track";

        HttpUtility http = new HttpUtility();
        http.makeRequest(url, access_token, new HttpUtility.DataCallbackInterface() {
            @Override
            public void onDataFetch(String result) {
                Log.d("data fetch result", result);
            }
        });

    }
}
