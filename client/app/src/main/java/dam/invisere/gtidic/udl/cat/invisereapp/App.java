package dam.invisere.gtidic.udl.cat.invisereapp;

import android.app.Application;

import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;

public class App extends Application {
    String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        Preferences.init(this);
    }
}
