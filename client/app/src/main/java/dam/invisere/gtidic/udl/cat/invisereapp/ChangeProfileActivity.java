package dam.invisere.gtidic.udl.cat.invisereapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;

import static android.content.ContentValues.TAG;

public class ChangeProfileActivity extends AppCompatActivity {

    public static TextInputLayout textName;
    public static TextInputLayout textUsername;
    public static TextInputLayout textEmail;

    public String name;
    public String username;
    public String email;

    private Button btnCancel;
    private Button btnSave;

    private AccountRepo accountRepo;


    public ChangeProfileActivity(){
        this.accountRepo = new AccountRepo();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        textEmail = findViewById(R.id.txtChangeEmail);
        textName = findViewById(R.id.txtChangeName);
        textUsername = findViewById(R.id.txtChangeUsername);

        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);

        textName.getEditText().setText(ProfileActivity.name);
        textUsername.getEditText().setText(ProfileActivity.username);
        textEmail.getEditText().setText(ProfileActivity.email);
    }


    public void onStart(){
        super.onStart();

        btnSave.setOnClickListener(v -> {
            editName();
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        });

        btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        });


    }


    public void editName(){

        Log.d(TAG, "edit Name");

        name = textName.getEditText().getText().toString();
        username = textUsername.getEditText().getText().toString();
        email = textEmail.getEditText().getText().toString();

        Account account = new Account();

        if(name.compareTo(ProfileActivity.name) == 0){
            Log.d(TAG, "editName: name = null");
            account.setName(null);
        }
        else{
            account.setName(name);
        }

        if(username.compareTo(ProfileActivity.username) == 0){
            Log.d(TAG, "editName: username = null");
            account.setUsername(null);
        }
        else{
            account.setUsername(username);
        }

        if(email.compareTo(ProfileActivity.email) == 0){
            Log.d(TAG, "editName: email = null");
            account.setEmail(null);
        }
        else{
            account.setEmail(email);
        }

        account.setPassword(null);


        String token = Preferences.providePreferences().getString("token", "");
        Log.d(TAG, "editName -> he rebut el token: " + token);


        accountRepo.update(account,token);
    }
}