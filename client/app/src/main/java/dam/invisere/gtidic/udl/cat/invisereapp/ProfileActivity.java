package dam.invisere.gtidic.udl.cat.invisereapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import dam.invisere.gtidic.udl.cat.invisereapp.models.AccountProfile;
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

    public static void updateFields(AccountProfile accountProfile){

        createdAt = accountProfile.getCreated_at();
        name = accountProfile.getName();
        username = accountProfile.getUsername();
        email = accountProfile.getEmail();
        UrlPhoto = accountProfile.getPhoto();

        Log.d(TAG, "CreatedAt: " + createdAt);
        Log.d(TAG, "Name: " + name);
        Log.d(TAG, "Username: " + username);
        Log.d(TAG, "Email: " + email);
        Log.d(TAG, "UrlPhoto: " + UrlPhoto);

        textCreatedAt.setText(createdAt);
        textName.setText(name);
        textUsername.setText(username);
        textEmail.setText(email);

        if(UrlPhoto != null){
            UrlPhoto = UrlPhoto.replace("http://127.0.0.1:8001","http://192.168.43.206:8001");
            //UrlPhoto = UrlPhoto.replace("http://127.0.0.1:8001","http://192.168.1.101:8001");
            Log.d(TAG, "UrlPhoto2: " + UrlPhoto);

            Picasso.get().load(UrlPhoto).error(R.drawable.ic_launcher_background).resize(350, 350).into(photoImage);
        }
        else {
            Picasso.get().load("https://previews.123rf.com/images/boxerx/boxerx1611/boxerx161100006/68882648-descargar-signo-en-fondo-blanco-cargar-icono-barra-de-carga-de-datos-ilustraci%C3%B3n-de-stock-vector.jpg").error(R.drawable.ic_launcher_background).resize(350, 350).into(photoImage);
        }
    }


}

