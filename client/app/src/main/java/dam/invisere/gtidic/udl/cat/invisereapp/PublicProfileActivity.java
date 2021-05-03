package dam.invisere.gtidic.udl.cat.invisereapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import dam.invisere.gtidic.udl.cat.invisereapp.models.AccountProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.models.PublicProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;

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

    private Button btn;

    public PublicProfileActivity() {

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

        putProfile();
    }

    protected void onStart() {
        super.onStart();

        btn.setOnClickListener(v -> {
            finish();
        });
    }

    public void putProfile(){
        PublicProfile publicProfile = new PublicProfile();
        publicProfile.setRutes(10);
        publicProfile.setFav(5);
        publicProfile.setName("David");
        publicProfile.setPhoto("https://image.freepik.com/vector-gratis/perfil-avatar-hombre-icono-redondo_24640-14044.jpg");
        publicProfile.setUsername("DavidArt");
        updateFields(publicProfile);
    }


    public static void updateFields(PublicProfile publicProfile){

        name = publicProfile.getName();
        username = publicProfile.getUsername();
        rutes = publicProfile.getRutes();
        UrlPhoto = publicProfile.getPhoto();
        fav = publicProfile.getFav();

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