package es.headfon.headfones

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import java.util.*

class MainActivity : AppCompatActivity() {
    private var mAccessToken: String? = null

    companion object {
        lateinit var spotifyAppRemote: SpotifyAppRemote;
        val AUTH_TOKEN_REQUEST_CODE = 0x10;
    }

    fun connectSpotifyRemote(showAuthView: Boolean) {
        SpotifyAppRemote.disconnect(spotifyAppRemote)
        SpotifyAppRemote.connect(
                application,
                ConnectionParams.Builder(Constants.CLIENT_ID)
                        .setRedirectUri(Constants.REDIRECT_URI)
                        .showAuthView(showAuthView)
                        .build(),
                object : Connector.ConnectionListener {
                    override fun onConnected(sAR: SpotifyAppRemote) {
                        spotifyAppRemote = sAR;
                    }

                    override fun onFailure(error: Throwable) {}
                })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.title = String.format(
                Locale.US, "Spotify Auth Sample %s", com.spotify.sdk.android.authentication.BuildConfig.VERSION_NAME)
    }


    fun onLoginClick(view: View) {
        val request = getAuthenticationRequest(AuthenticationResponse.Type.TOKEN)
        AuthenticationClient.openLoginActivity(this, AUTH_TOKEN_REQUEST_CODE, request)
    }

    fun goToSearchScreen() {
        val intent = Intent(this, SearchSpotifyActivity::class.java)
        intent.putExtra("access_token", mAccessToken)
        startActivity(intent)
    }

    private fun getAuthenticationRequest(type: AuthenticationResponse.Type): AuthenticationRequest {
        return AuthenticationRequest.Builder(Constants.CLIENT_ID, type, Constants.REDIRECT_URI)
                .setShowDialog(false)
                .setScopes(arrayOf("user-read-email"))
                .build()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val response = AuthenticationClient.getResponse(resultCode, data)

        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            mAccessToken = response.accessToken
            goToSearchScreen()
            connectSpotifyRemote(false)
        }
    }
}