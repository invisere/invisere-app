package dam.invisere.gtidic.udl.cat.invisereapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import dam.invisere.gtidic.udl.cat.invisereapp.databinding.ActivityProfileBinding;
import dam.invisere.gtidic.udl.cat.invisereapp.models.AccountProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.ProfileActivityViewModel;

import static android.content.ContentValues.TAG;

public class ProfileActivity extends AppCompatActivity {

    private ProfileActivityViewModel profileActivityViewModel;

    public static TextInputLayout textName;
    public static TextInputLayout textUsername;
    public static TextInputLayout textEmail;

    public static ImageView photoImage;

    public static String name;
    public static String username;
    public static String email;
    public static String UrlPhoto;

    private Button btn;
    private Button btnChangeProfile;
    private ImageButton btnEdit;

    private AccountRepo accountRepo;

    String token = Preferences.providePreferences().getString("token", "");

    public ProfileActivity() {
        this.accountRepo = new AccountRepo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileActivityViewModel = new ViewModelProvider(this).get(ProfileActivityViewModel.class);
        ActivityProfileBinding activityProfileBinding = DataBindingUtil.

        textName = findViewById(R.id.TextField_name_profile);
        textName.setEnabled(false);
        textUsername = findViewById(R.id.TextField_username_profile);
        textUsername.setEnabled(false);
        textEmail = findViewById(R.id.TextField_email_profile);
        textEmail.setEnabled(false);
        btn = findViewById(R.id.buttonBack);
        btnChangeProfile = findViewById(R.id.btnChangeProfile);
        btnEdit = findViewById(R.id.buttonEdit);

        photoImage = findViewById(R.id.photoProfile);

        accountRepo.get_account(token);

    }

    protected void onStart() {
        super.onStart();

        btn.setOnClickListener(v -> {
            finish();
        });

        btnChangeProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ChangeProfileActivity.class);
            startActivity(intent);
            finish();
        });

        btnEdit.setOnClickListener(v -> {
            textName.setEnabled(true);
            textUsername.setEnabled(true);
            textEmail.setEnabled(true);
        });

    }

    public static void updateFields(AccountProfile accountProfile){

        name = accountProfile.getName();
        username = accountProfile.getUsername();
        email = accountProfile.getEmail();
        UrlPhoto = accountProfile.getPhoto();

        Log.d(TAG, "Name: " + name);
        Log.d(TAG, "Username: " + username);
        Log.d(TAG, "Email: " + email);
        Log.d(TAG, "UrlPhoto: " + UrlPhoto);

        textName.getEditText().setText(name);
        textUsername.getEditText().setText(username);
        textEmail.getEditText().setText(email);

        if(UrlPhoto != null){
            UrlPhoto = UrlPhoto.replace("http://127.0.0.1:8001","http://192.168.101.88:8001");
            Log.d(TAG, "UrlPhoto2: " + UrlPhoto);

            Picasso.get().load(UrlPhoto).error(R.drawable.ic_launcher_background).resize(350, 350).into(photoImage);
        }
        else {
            Picasso.get().load("https://previews.123rf.com/images/boxerx/boxerx1611/boxerx161100006/68882648-descargar-signo-en-fondo-blanco-cargar-icono-barra-de-carga-de-datos-ilustraci%C3%B3n-de-stock-vector.jpg").error(R.drawable.ic_launcher_background).resize(350, 350).into(photoImage);
        }
    }


}

