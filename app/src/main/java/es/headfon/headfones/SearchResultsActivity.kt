package es.headfon.headfones

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.AdapterView
import android.widget.ListView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class SearchResultsActivity : AppCompatActivity() {
    private var lv: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)
        val intent = intent

        // parse the passed search results
        try {
            val searchResults = JSONObject(intent.extras!!.getString("search_results"))
            val albums = searchResults.getJSONObject("albums")
            val albumList = albums.getJSONArray("items")
            populateListView(albumList)
        } catch (e: JSONException) {
            Log.d("Error parsing JSON", e.toString())
        }

    }

    fun populateListView(items: JSONArray) {
        // TODO: re-think structure to include other types
        var allItems = items
        val albums = ArrayList<SearchAlbumListing>()
        try {
            for (i in 0 until items.length()) {
                val item = items.getJSONObject(i)
                val images = item.getJSONArray("images")
                val image = images.getJSONObject(0)
                albums.add(SearchAlbumListing(item.getString("name"), image.getString("url")))
            }
        } catch (e: JSONException) {
            Log.d("error parsing json", e.toString())
        }

        lv = findViewById<ListView>(R.id.list)
        lv!!.adapter = SearchAlbumAdapter(
                this,
                albums
        )

        // Set an item click listener for ListView
        lv!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // Get the selected item text from ListView
            try {
                val uri = allItems.getJSONObject(position).getString("uri")
                val remote = MainActivity.getSpotifyAppRemote()
                remote.playerApi.play(uri)
            } catch (e: JSONException) {
                Log.d("JSON Parse Failed", e.toString())
            }
        }
    }
}
