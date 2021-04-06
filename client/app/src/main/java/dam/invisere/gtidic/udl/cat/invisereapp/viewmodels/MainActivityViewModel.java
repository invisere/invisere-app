package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;


public class MainActivityViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";

    public MainActivityViewModel() {
    }

    public boolean isLogged() {
        String token = Preferences.providePreferences().getString("token", "");
        Log.d(TAG, "isLogged() -> he rebut el token: " + token);
        return !token.isEmpty();
    }

}
