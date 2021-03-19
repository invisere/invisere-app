package dam.invisere.gtidic.udl.cat.invisereapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import dam.invisere.gtidic.udl.cat.invisereapp.models.AccountUser;
import dam.invisere.gtidic.udl.cat.invisereapp.models.LoginUtils;

public class RegisterActivity extends AppCompatActivity {

    private Button cancel_register;
    private Button register;

    public TextInputLayout textName;
    public TextInputLayout textSurname;
    public TextInputLayout textEmail;
    public TextInputLayout textPassword;
    public TextInputLayout textConfirmPassword;

    String TAG = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        cancel_register = findViewById(R.id.Button_cancel_register);
        register = findViewById(R.id.Button_register);

        textName = findViewById(R.id.TextField_name_register);
        textSurname = findViewById(R.id.TextField_surname_register);
        textPassword = findViewById(R.id.TextField_password_register);
        textConfirmPassword = findViewById(R.id.TextField_confirm_password_register);
        textEmail = findViewById(R.id.TextField_email_register);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        cancel_register.setOnClickListener(v -> {
            startActivity(intent);
        });

        register.setOnClickListener(v -> {
            String name = textName.getEditText().getText().toString();
            String surname = textSurname.getEditText().getText().toString();
            String email = textEmail.getEditText().getText().toString();
            String password = textPassword.getEditText().getText().toString();
            String confirmPassword = textConfirmPassword.getEditText().getText().toString();

            boolean cN = LoginUtils.checkName(name, textName);
            boolean cS = LoginUtils.checkSurname(surname, textSurname);
            boolean vE = LoginUtils.isValidEmailAddress(email, textEmail);
            boolean cP = LoginUtils.checkPassword(password,confirmPassword, textPassword,textConfirmPassword);

            if (cN && cP && cS && vE){

                startActivity(intent);
            }

        });


    }
}