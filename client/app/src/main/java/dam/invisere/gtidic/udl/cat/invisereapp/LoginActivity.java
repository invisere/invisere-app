package dam.invisere.gtidic.udl.cat.invisereapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button cancel_login;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cancel_login = findViewById(R.id.Button_cancel_login);
        login = findViewById(R.id.Button_login);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        cancel_login.setOnClickListener(v -> {
            startActivity(intent);
        });

        login.setOnClickListener(v -> {
            startActivity(intent);
        });
    }
}