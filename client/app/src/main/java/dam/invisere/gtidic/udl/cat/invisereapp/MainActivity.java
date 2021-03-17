package dam.invisere.gtidic.udl.cat.invisereapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
  
    private Button login;
    private Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_InvisereApp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.Button_to_login_activity);
        register = findViewById(R.id.Button_to_register_activity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        login.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });
    }
}