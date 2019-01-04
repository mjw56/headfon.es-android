package es.headfon.headfones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

public class SearchSpotifyActivity extends AppCompatActivity {
    private String access_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_spotify);
        Intent intent = getIntent();
        access_token = intent.getExtras().getString("access_token");
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            onSearchClick(findViewById(R.id.searchText));
            return true;
        }
        return super.dispatchKeyEvent(e);
    };

    public void onSearchClick(View view) {
        view.clearFocus();
        EditText search  = findViewById(R.id.searchText);
        String url = "https://api.spotify.com/v1/search/?q=" + search.getText().toString() + "*&type=album,artist,playlist,track";
        HttpUtility http = new HttpUtility();

        http.makeRequest(url, access_token, new HttpUtility.DataCallbackInterface() {
            @Override
            public void onDataFetch(JSONObject result) {
                Intent intent = new Intent(getApplicationContext(), SearchResultsActivity.class);
                intent.putExtra("search_results", result.toString());
                startActivity(intent);
            }
        });

    }
}
