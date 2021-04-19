package dam.invisere.gtidic.udl.cat.invisereapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.EntryActivityViewModel;

public class EntryActivity extends AppCompatActivity {

    private static final String TAG = "Entry Activity";
    public static Context mContext;

    EntryActivityViewModel entryActivityViewModel = new EntryActivityViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_InvisereApp);
        super.onCreate(savedInstanceState);

        Preferences.init(this);

        if(entryActivityViewModel.isLogged()){
            Log.d(TAG, "onCreate () -> existe un token");

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Log.d(TAG, "Entry Activity() -> No existe token");
            setContentView(R.layout.activity_entry);
        }

        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }

}

