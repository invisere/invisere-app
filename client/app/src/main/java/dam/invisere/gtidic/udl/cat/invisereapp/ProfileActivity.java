package dam.invisere.gtidic.udl.cat.invisereapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;

import static android.content.ContentValues.TAG;

public class ProfileActivity extends AppCompatActivity {

    public static TextView textCreatedAt;
    public static TextView textName;
    public static TextView textUsername;
    public static TextView textEmail;


    public static ImageView photoImage;

    public static String createdAt;
    public static String name;
    public static String username;
    public static String email;
    public static String UrlPhoto;

    public static String nullPhoto;

    static String[] arrSplit;
    private Button btn;
    private Button btnChangeProfile;

    private AccountRepo accountRepo;


    String token = Preferences.providePreferences().getString("token", "");


    public ProfileActivity() {
        this.accountRepo = new AccountRepo();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textCreatedAt = findViewById(R.id.txtCreatedAt);
        textName = findViewById(R.id.txtName);
        textUsername = findViewById(R.id.txtUsername);
        textEmail = findViewById(R.id.txtEmail);
        btn = findViewById(R.id.button3);
        btnChangeProfile = findViewById(R.id.btnChangeProfile);

        photoImage = findViewById(R.id.photoProfile);

        accountRepo.get_account(token);

    }

    protected void onStart() {
        super.onStart();


        btn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });


        btnChangeProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ChangeProfileActivity.class);
            startActivity(intent);
        });

    }

    public static void updateFields() throws IOException {

        arrSplit = AccountRepo.profile.split("\"");

        nullPhoto = ": null}";

        for (int i = 0; i < arrSplit.length; i++) {
            Log.d(TAG, "ArraySplit: " + arrSplit[i]);
        }

        createdAt = arrSplit[3];
        Log.d(TAG, "CreatedAt: " + createdAt);

        textCreatedAt.setText(createdAt);

        name = arrSplit[7];
        Log.d(TAG, "Name: " + name);

        textName.setText(name);

        username = arrSplit[11];
        Log.d(TAG, "Username: " + username);

        textUsername.setText(username);

        email = arrSplit[15];
        Log.d(TAG, "Email: " + email);

        textEmail.setText(email);


        if (arrSplit[18].compareTo(nullPhoto) == 0) {
            UrlPhoto = null;
            Log.d(TAG, "UrlPhoto: " + UrlPhoto);
        } 
        else {
            UrlPhoto = arrSplit[19];
            Log.d(TAG, "UrlPhoto: " + UrlPhoto);

            Picasso.get().load(UrlPhoto).error(R.drawable.ic_launcher_background).into(photoImage);
        }
    }

}

