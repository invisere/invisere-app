package dam.invisere.gtidic.udl.cat.invisereapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.Utils;

import static android.content.ContentValues.TAG;

public class MainActivity extends AuthActivity {

    private Button btn4;
    private Button btnLogout;
    private Button btnProfile;
    private Button btnRecomendRoutes;
    private AccountRepo accountRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }


    private void initView() {
        setTheme(R.style.Theme_InvisereApp);
        setContentView(R.layout.activity_main);
        btn4 = findViewById(R.id.button4);
        btnLogout = findViewById(R.id.button_logout);
        btnProfile = findViewById(R.id.button_profile);
        btnRecomendRoutes = findViewById(R.id.btnRecomend);
        accountRepo = new AccountRepo();
    }

    private void logout(View view) {

        accountRepo.deleteTokenUser(Utils.getToken());
        Log.d(TAG, "Logout() -> he rebut el token: " + Utils.getToken());

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
            accountRepo.get_public_account(Utils.getToken(),"user2");
            Intent intent = new Intent(getApplicationContext(), PublicProfileActivity.class);
            startActivity(intent);
        });

        btnRecomendRoutes.setOnClickListener(v -> {
            Log.d(TAG, "onViewPublicProfile()");
            Intent intent = new Intent(getApplicationContext(), RoutesListActivity.class);
            startActivity(intent);
        });
    }
}