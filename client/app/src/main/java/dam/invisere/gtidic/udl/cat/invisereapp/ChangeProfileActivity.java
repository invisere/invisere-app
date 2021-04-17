package dam.invisere.gtidic.udl.cat.invisereapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.File;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.content.ContentValues.TAG;

public class ChangeProfileActivity extends AppCompatActivity {

    public static TextInputLayout textName;
    public static TextInputLayout textUsername;
    public static TextInputLayout textEmail;
    public static TextInputLayout textPassword;

    public String name;
    public String username;
    public String email;
    public String password;

    private boolean photoChanged = false;

    public ImageView profileImage;

    private Button btnCancel;
    private Button btnSave;
    private Button btnChangePhoto;
    private Button btnDeletePhoto;

    private static final int PICK_IMAGE = 100;
    public Uri imageUri;

    private AccountRepo accountRepo;

    String token = Preferences.providePreferences().getString("token", "");

    private static final int CODIGO_PERMISOS_EXTERNAL_STORAGE = 1;
    public static int checkEstadoPermisoExternalMemory = ContextCompat.checkSelfPermission(EntryActivity.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);


    public ChangeProfileActivity() {
        this.accountRepo = new AccountRepo();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        textEmail = findViewById(R.id.txtChangeEmail);
        textName = findViewById(R.id.txtChangeName);
        textUsername = findViewById(R.id.txtChangeUsername);
        textPassword = findViewById(R.id.txtChangePassword);

        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        btnChangePhoto = findViewById(R.id.button4);
        btnDeletePhoto = findViewById(R.id.button5);

        profileImage = findViewById(R.id.imageView);

        Picasso.get().load(ProfileActivity.UrlPhoto).error(R.drawable.ic_launcher_background).resize(350, 350).into(profileImage);
        textName.getEditText().setText(ProfileActivity.name);
        textUsername.getEditText().setText(ProfileActivity.username);
        textEmail.getEditText().setText(ProfileActivity.email);
        textPassword.getEditText().setText("");
    }


    public void onStart() {
        super.onStart();

        btnSave.setOnClickListener(v -> {
            updateProfile();
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        });

        btnCancel.setOnClickListener(v -> {
            finish();
        });

        btnChangePhoto.setOnClickListener(v -> {
            checkPermitExternalMemory();
        });

        btnDeletePhoto.setOnClickListener(v -> {

        });

    }


    public void updateProfile() {

        Log.d(TAG, "updateProfile");

        name = textName.getEditText().getText().toString();
        username = textUsername.getEditText().getText().toString();
        email = textEmail.getEditText().getText().toString();
        password = textPassword.getEditText().getText().toString();

        Account account = new Account();

        if (name.compareTo(ProfileActivity.name) == 0) {
            Log.d(TAG, "updateProfile: name = null");
            account.setName(null);
        } else {
            account.setName(name);
        }

        if (username.compareTo(ProfileActivity.username) == 0) {
            Log.d(TAG, "updateProfile: username = null");
            account.setUsername(null);
        } else {
            account.setUsername(username);
        }

        if (email.compareTo(ProfileActivity.email) == 0) {
            Log.d(TAG, "updateProfile: email = null");
            account.setEmail(null);
        } else {
            account.setEmail(email);
        }

        if (password.compareTo("") == 0) {
            Log.d(TAG, "updateProfile: password = null");
            account.setPassword(null);
        } else {
            account.setPassword(password);
        }


        Log.d(TAG, "updateProfile -> he rebut el token: " + token);


        if(photoChanged == true){
            File file = new File(getRealPathFromURI(imageUri,this));

            RequestBody requestFile = RequestBody.create(file,MediaType.parse("image/*"));
            MultipartBody.Part body2 = MultipartBody.Part.createFormData("image_file", file.getName(), requestFile);

            Log.d(TAG, "updatePhoto -> he rebut el urL: " + imageUri.toString());
            Log.d(TAG, "updatePhoto -> he rebut el token: " + ProfileActivity.UrlPhoto);

            accountRepo.updatePhoto(body2,token);
        }

        if(account.getEmail() == null && account.getUsername() == null && account.getName() == null && account.getPassword() == null){

        }
        else{
            accountRepo.update(account, token);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).error(R.drawable.ic_launcher_background).resize(350, 350).into(profileImage);
            photoChanged = true;
        }
        else{
            photoChanged = false;
        }
    }


    public String getRealPathFromURI(Uri uri, Activity activity) {

         if (uri == null) {
             return null;
         }
         String[] projection = {MediaStore.Images.Media.DATA};
         @SuppressLint("Recycle") Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);

         if (cursor != null) {
             int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
         }
         return uri.getPath();
    }


    public void checkPermitExternalMemory(){
        if(checkEstadoPermisoExternalMemory == PackageManager.PERMISSION_GRANTED){
            permisoDeAlmacenamientoConcedido();
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, PICK_IMAGE);

        }
        else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    CODIGO_PERMISOS_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CODIGO_PERMISOS_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestResult Concedido");

                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, PICK_IMAGE);

                    permisoDeAlmacenamientoConcedido();
                }
                else {
                    Log.d(TAG, "onRequestResult no Concedido");
                    permisoDeAlmacenamientoNoConcedido();
                }
                break;
        }

    }

    private static void permisoDeAlmacenamientoConcedido() {
        Toast.makeText(EntryActivity.getContext(), "El permiso para el almacenamiento está concedido", Toast.LENGTH_SHORT).show();

    }

    private static void permisoDeAlmacenamientoNoConcedido() {
        Toast.makeText(EntryActivity.getContext(), "El permiso para el almacenamiento no está concedido", Toast.LENGTH_SHORT).show();
    }


}
