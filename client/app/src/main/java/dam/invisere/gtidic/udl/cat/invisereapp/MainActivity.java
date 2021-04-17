package dam.invisere.gtidic.udl.cat.invisereapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Token;
import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private Button btn2;
    private AccountRepo accountRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        accountRepo = new AccountRepo();

    }

    @Override
    protected void onStart() {
        super.onStart();

        btn.setOnClickListener(v -> {

                String token = Preferences.providePreferences().getString("token","");
                accountRepo.deleteTokenUser(new Token(token), token);
                Log.d(TAG, "Logout() -> he rebut el token: " + token);

                Preferences.providePreferences().edit().clear().apply();

                Intent intent = new Intent(getApplicationContext(), EntryActivity.class);
                startActivity(intent);

        });


        btn2.setOnClickListener(v -> {
            Log.d(TAG, "onViewProfile()");

            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        });
    }
}