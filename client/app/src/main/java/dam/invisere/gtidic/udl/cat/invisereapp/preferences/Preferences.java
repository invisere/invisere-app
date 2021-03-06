package dam.invisere.gtidic.udl.cat.invisereapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private static SharedPreferences preferences;

    public static SharedPreferences providePreferences() {
        return preferences;
    }

    public static void init(Context context) {
        String shared_preferences = "Preferences";
        preferences = context.getSharedPreferences(shared_preferences, Context.MODE_PRIVATE);
    }
}
