package es.headfon.headfones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.spotify.android.appremote.api.SpotifyAppRemote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchResultsActivity extends AppCompatActivity {
    private ListView lv;
    JSONArray allItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Intent intent = getIntent();

        // parse the passed search results
        try {
            JSONObject searchResults = new JSONObject(intent.getExtras().getString("search_results"));
            JSONObject albums = searchResults.getJSONObject("albums");
            JSONArray albumList = albums.getJSONArray("items");
            populateListView(albumList);
        } catch(JSONException e) {
            Log.d("Error parsing JSON", e.toString());
        }
    }

    public void populateListView(JSONArray items) {
        // TODO: re-think structure to include other types
        allItems = items;
        ArrayList<SearchAlbumListing> albums = new ArrayList<>();
        try {
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONArray images = item.getJSONArray("images");
                JSONObject image = images.getJSONObject(0);
                albums.add(new SearchAlbumListing(item.getString("name"), image.getString("url")));
            }
        } catch (JSONException e) {
            Log.d("error parsing json", e.toString());
        }

        lv = findViewById(R.id.list);
        lv.setAdapter(new SearchAlbumAdapter(
            this,
            albums
        ));

        // Set an item click listener for ListView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                try {
                    String uri = allItems.getJSONObject(position).getString("uri");
                    SpotifyAppRemote remote = MainActivity.getSpotifyAppRemote();
                    remote.getPlayerApi().play(uri);
                } catch (JSONException e) {
                    Log.d("JSON Parse Failed", e.toString());
                }
            }
        });
    }
}
