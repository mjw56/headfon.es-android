package es.headfon.headfones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final int AUTH_TOKEN_REQUEST_CODE = 0x10;
    private String mAccessToken;
    private static SpotifyAppRemote mSpotifyAppRemote;

    public static SpotifyAppRemote getSpotifyAppRemote() {
        return mSpotifyAppRemote;
    }

    public void connectSpotifyRemote(boolean showAuthView) {
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
        SpotifyAppRemote.connect(
                getApplication(),
                new ConnectionParams.Builder(Constants.INSTANCE.getCLIENT_ID())
                        .setRedirectUri(Constants.INSTANCE.getREDIRECT_URI())
                        .showAuthView(showAuthView)
                        .build(),
                new Connector.ConnectionListener() {
                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                    }

                    @Override
                    public void onFailure(Throwable error) {}
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(String.format(
                Locale.US, "Spotify Auth Sample %s", com.spotify.sdk.android.authentication.BuildConfig.VERSION_NAME));
    }


    public void onLoginClick(View view) {
        final AuthenticationRequest request = getAuthenticationRequest(AuthenticationResponse.Type.TOKEN);
        AuthenticationClient.openLoginActivity(this, AUTH_TOKEN_REQUEST_CODE, request);
    }

    public void goToSearchScreen() {
        Intent intent = new Intent(this, SearchSpotifyActivity.class);
        intent.putExtra("access_token", mAccessToken);
        startActivity(intent);
    }

    private AuthenticationRequest getAuthenticationRequest(AuthenticationResponse.Type type) {
        return new AuthenticationRequest.Builder(Constants.INSTANCE.getCLIENT_ID(), type, Constants.INSTANCE.getREDIRECT_URI())
        .setShowDialog(false)
            .setScopes(new String[]{"user-read-email"})
            .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);

        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            mAccessToken = response.getAccessToken();
            goToSearchScreen();
            connectSpotifyRemote(false);
        }
    }
}