package es.headfon.headfones

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.widget.EditText

import org.json.JSONObject

class SearchSpotifyActivity : AppCompatActivity() {
    private var access_token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_spotify)
        val intent = intent
        access_token = intent.extras!!.getString("access_token")
    }

    override fun dispatchKeyEvent(e: KeyEvent): Boolean {
        if (e.keyCode == KeyEvent.KEYCODE_ENTER) {
            onSearchClick(findViewById(R.id.searchText))
            return true
        }
        return super.dispatchKeyEvent(e)
    }

    fun onSearchClick(view: View) {
        view.clearFocus()
        val search = findViewById<EditText>(R.id.searchText)
        val url = "https://api.spotify.com/v1/search/?q=" + search.text.toString() + "*&type=album,artist,playlist,track"
        val http = HttpUtility()

        http.makeRequest(url, access_token!!, object : HttpUtility.DataCallbackInterface {
            override fun onDataFetch(result: JSONObject) {
                val intent = Intent(applicationContext, SearchResultsActivity::class.java)
                intent.putExtra("search_results", result.toString())
                startActivity(intent)
            }
        })

    }
}
