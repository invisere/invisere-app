package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import android.util.Log;

import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;

import static android.content.ContentValues.TAG;

public class MainActivityViewModel {

    public MainActivityViewModel(){
    }

    public boolean isLogged(){
        String token = Preferences.providePreferences().getString("token","");
        Log.d(TAG, "isLogged() -> he rebut el token: " + token);
        return !token.isEmpty();
    }
}
