package es.headfon.headfones;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;


public class SearchResultsActivity extends AppCompatActivity {
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Intent intent = getIntent();

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
        String[] itemname = new String[items.length()];
        try {
            for (int i = 0; i < items.length(); i++) {
                Log.d("test", items.getJSONObject(i).toString());
                JSONObject item = items.getJSONObject(i);
                itemname[i] = item.getString("name");
            }
        } catch (JSONException e) {
            Log.d("error parsing json", e.toString());
        }

        lv = (ListView)findViewById(R.id.list);
        lv.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.search_results_list,
                R.id.item_name,
                itemname)
        );
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
