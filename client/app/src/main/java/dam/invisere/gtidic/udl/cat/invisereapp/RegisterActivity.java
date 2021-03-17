package dam.invisere.gtidic.udl.cat.invisereapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    private Button cancel_register;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        cancel_register = findViewById(R.id.Button_cancel_register);
        register = findViewById(R.id.Button_register);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        cancel_register.setOnClickListener(v -> {
            startActivity(intent);
        });

        register.setOnClickListener(v -> {
            startActivity(intent);
        });
    }
}