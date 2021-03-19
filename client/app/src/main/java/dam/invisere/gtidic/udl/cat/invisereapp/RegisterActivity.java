package dam.invisere.gtidic.udl.cat.invisereapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import dam.invisere.gtidic.udl.cat.invisereapp.models.LoginUtils;

public class RegisterActivity extends AppCompatActivity {

    private Button cancel_register;
    private Button register;

    public TextInputLayout textName;
    public TextInputLayout textSurname;
    public TextInputLayout textEmail;
    public TextInputLayout textPassword;

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = this;

        cancel_register = findViewById(R.id.Button_cancel_register);
        register = findViewById(R.id.Button_register);

        textName = findViewById(R.id.TextField_name_register);
        textSurname = findViewById(R.id.TextField_username_register);
        textPassword = findViewById(R.id.TextField_password_register);
        textEmail = findViewById(R.id.TextField_email_register);

    }

    public static Context getContext(){
        return mContext;
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
            String username = textSurname.getEditText().getText().toString();
            String email = textEmail.getEditText().getText().toString();
            String password = textPassword.getEditText().getText().toString();

            boolean cN = LoginUtils.checkName(name, textName);
            boolean cS = LoginUtils.checkUsername(username, textSurname);
            boolean vE = LoginUtils.isValidEmailAddress(email, textEmail);
            boolean cP = LoginUtils.checkPassword(password, textPassword);

            if (cN && cS && vE && cP){
                startActivity(intent);
            }

        });


    }
}