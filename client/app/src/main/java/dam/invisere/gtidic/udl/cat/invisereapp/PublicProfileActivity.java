package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import dam.invisere.gtidic.udl.cat.invisereapp.models.AccountProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.models.PublicProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.Utils;

import static android.content.ContentValues.TAG;

public class PublicProfileActivity extends AppCompatActivity {

    public static TextInputLayout textName;
    public static TextInputLayout textUsername;
    public static TextInputLayout textRutes;
    public static TextInputLayout textFav;

    public static ImageView photoImage;

    public static String name;
    public static String username;
    public static int rutes;
    public static int fav;
    public static String UrlPhoto;

    public static PublicProfile publicProfile;

    String username2;

    private AccountProfile accountProfile = new Gson().fromJson(Preferences.providePreferences().getString("account", ""), AccountProfile.class);

    private Button btn;

    private AccountRepo accountRepo;

    public PublicProfileActivity() {
        this.accountRepo = new AccountRepo();
        username2 = accountProfile.getUsername();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        textName = findViewById(R.id.TextNamePublicProfile);
        textName.setEnabled(false);
        textUsername = findViewById(R.id.TextUsernamePublicProfile);
        textUsername.setEnabled(false);
        textRutes = findViewById(R.id.TextField_rutes_profile);
        textRutes.setEnabled(false);
        textFav = findViewById(R.id.TextField_fav_profile);
        textFav.setEnabled(false);
        btn = findViewById(R.id.ButtonBack);

        photoImage = findViewById(R.id.PhotoPublicProfile);

        accountRepo.get_public_account(username2,Utils.getToken());

        putProfile();
    }

    protected void onStart() {
        super.onStart();

        btn.setOnClickListener(v -> {
            finish();
        });
    }

    public void putProfile(){
        publicProfile = new Gson().fromJson(Preferences.providePreferences().getString("accountProfile", ""), PublicProfile.class);

        updateFields();
    }


    public static void updateFields(){

        name = publicProfile.getName();
        username = publicProfile.getUsername();
        UrlPhoto = publicProfile.getPhoto();
        rutes = publicProfile.getRutes();


        Log.d(TAG, "Name: " + name);
        Log.d(TAG, "Username: " + username);
        Log.d(TAG, "rutes: " + rutes);
        Log.d(TAG, "UrlPhoto: " + UrlPhoto);

        textName.getEditText().setText(name);
        textUsername.getEditText().setText(username);
        textRutes.getEditText().setText("" + rutes);
        textFav.getEditText().setText("" + fav);

        if(UrlPhoto != null){
            //UrlPhoto = UrlPhoto.replace("http://127.0.0.1:8001","http://192.168.101.88:8001");
            Log.d(TAG, "UrlPhoto2: " + UrlPhoto);

            Picasso.get().load(UrlPhoto).error(R.drawable.ic_launcher_background).resize(350, 350).into(photoImage);
        }
        else {
            Picasso.get().load("https://previews.123rf.com/images/boxerx/boxerx1611/boxerx161100006/68882648-descargar-signo-en-fondo-blanco-cargar-icono-barra-de-carga-de-datos-ilustraci%C3%B3n-de-stock-vector.jpg").error(R.drawable.ic_launcher_background).resize(350, 350).into(photoImage);
        }
    }


}