package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class EntryActivity extends AppCompatActivity {

    private static final String TAG = "Entry Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        Log.d(TAG, "onCreate()");
    }

}

