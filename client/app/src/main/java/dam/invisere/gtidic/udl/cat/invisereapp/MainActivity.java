package dam.invisere.gtidic.udl.cat.invisereapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;

import static android.content.ContentValues.TAG;

public class MainActivity extends AuthActivity {

<<<<<<< HEAD
    private Button btn;
    private Button btn2;
    private Button btn4;
=======
    private Button btnLogout;
    private Button btnProfile;
>>>>>>> a834909826b0faa683a5e73ff179fb9319bab899
    private AccountRepo accountRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

<<<<<<< HEAD
        btn = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        btn4 = findViewById(R.id.button4);
=======
    private void initView() {
        setTheme(R.style.Theme_InvisereApp);
        setContentView(R.layout.activity_main);
        btnLogout = findViewById(R.id.button_logout);
        btnProfile = findViewById(R.id.button_profile);
>>>>>>> a834909826b0faa683a5e73ff179fb9319bab899
        accountRepo = new AccountRepo();
    }

    private void logout(View view) {
        String token = Preferences.providePreferences().getString("token","");
        accountRepo.deleteTokenUser(token);
        Log.d(TAG, "Logout() -> he rebut el token: " + token);

        Preferences.providePreferences().edit().clear().apply();

        super.isLogged();
    }

    @Override
    protected void onStart() {
        super.onStart();

        btnProfile.setOnClickListener(v -> {
            Log.d(TAG, "onViewProfile()");

            Intent intent = new Intent(getApplicationContext(), PrivateProfileActivity.class);
            startActivity(intent);
        });

        btn4.setOnClickListener(v -> {
            Log.d(TAG, "onViewPublicProfile()");

            Intent intent = new Intent(getApplicationContext(), PublicProfileActivity.class);
            startActivity(intent);
        });
    }
}