package dam.invisere.gtidic.udl.cat.invisereapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.AuthActivityViewModel;

public class AuthActivity extends AppCompatActivity {

    private static final String TAG = "AuthActivity";
    public AuthActivityViewModel authActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_auth);
        Log.d(TAG, "onCreate()");

        authActivityViewModel = new AuthActivityViewModel();
        isLogged();
    }

    public void isLogged() {
        if(!authActivityViewModel.isLogged()) {
            Log.d(TAG, "not logged");
            Intent intent = new Intent(this, EntryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}