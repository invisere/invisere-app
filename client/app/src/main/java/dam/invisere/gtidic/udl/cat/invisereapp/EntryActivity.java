package dam.invisere.gtidic.udl.cat.invisereapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

public class EntryActivity extends AppCompatActivity {

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_InvisereApp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}