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

    private Button btnLogout;
    private Button btnProfile;
    private AccountRepo accountRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setTheme(R.style.Theme_InvisereApp);
        setContentView(R.layout.activity_main);
        btnLogout = findViewById(R.id.button_logout);
        btnProfile = findViewById(R.id.button_profile);
        accountRepo = new AccountRepo();
    }

    private void logout(View view) {
        String token = Preferences.providePreferences().getString("token","");
        accountRepo.deleteTokenUser(token);
        Log.d(TAG, "Logout() -> he rebut el token: " + token);

        Preferences.providePreferences().edit().clear().apply();

        super.isLogged();
    }

    private void profile(View view) {
        Intent intent = new Intent(getApplicationContext(), PrivateProfileActivity.class);
        startActivity(intent);
    }

}