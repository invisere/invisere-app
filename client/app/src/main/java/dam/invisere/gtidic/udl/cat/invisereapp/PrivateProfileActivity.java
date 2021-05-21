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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;

import dam.invisere.gtidic.udl.cat.invisereapp.databinding.ActivityPrivateProfileBinding;
import dam.invisere.gtidic.udl.cat.invisereapp.models.AccountProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.Utils;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.PrivateProfileActivityViewModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PrivateProfileActivity extends AuthActivity {

    private String TAG = "PrivateProfileActivity()";
    private PrivateProfileActivityViewModel privateProfileActivityViewModel;

    private ImageView ivProfilePhoto;
    private Button btnChangePhoto;
    private TextInputLayout txtName;
    private TextInputLayout txtUsername;
    private TextInputLayout txtEmail;
    private Button btnSubmitChanges;

    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private static final int EXTERNAL_STORAGE_PERMISSION_CODE = 1;
    private AccountRepo accountRepo = new AccountRepo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_profile);
        initViewModel();
        ivProfilePhoto = findViewById(R.id.photoProfile);
        btnChangePhoto = findViewById(R.id.buttonChangePhoto);
        txtName = findViewById(R.id.TextField_name_profile);
        txtUsername = findViewById(R.id.TextField_username_profile);
        txtEmail = findViewById(R.id.TextField_email_profile);
        btnSubmitChanges = findViewById(R.id.buttonSubmitChanges);
        toggle(getWindow().getDecorView());

        btnChangePhoto.setOnClickListener(v -> checkPermitExternalMemory());
        privateProfileActivityViewModel.Photo.observe(this, s -> {
            if(s.isEmpty()) {
                Picasso.get().load(R.drawable.ic_launcher_background).placeholder(R.drawable.progress_animation).resize(350, 350).into(ivProfilePhoto);
            } else {
                Picasso.get().load(s).error(R.drawable.ic_launcher_background).placeholder(R.drawable.progress_animation).resize(350, 350).into(ivProfilePhoto);
            }
        });

        privateProfileActivityViewModel.accountRepo.mResponseUpdate.observe(this, returnCodeI -> {
            if(returnCodeI.isSuccess() && returnCodeI.getReturnCode() == 200) {
                privateProfileActivityViewModel.accountRepo.get_account(Utils.getToken());
            }
        });

        privateProfileActivityViewModel.accountRepo.mResponseGetAccount.observe(this, returnCodeI -> {
            if (returnCodeI.getReturnCode() == 200) {
                privateProfileActivityViewModel.setAccountProfile(Utils.getAccountProfile());
                toggle(getWindow().getDecorView());
            }
        });
    }

    private void initViewModel() {
        privateProfileActivityViewModel = new PrivateProfileActivityViewModel();
        ActivityPrivateProfileBinding activityPrivateProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_private_profile);
        activityPrivateProfileBinding.setLifecycleOwner(this);
        activityPrivateProfileBinding.setViewModel(privateProfileActivityViewModel);
    }

    public void onBack(View view) {
        finish();
    }

    public void toggle(View view) {
        btnChangePhoto.setVisibility((btnChangePhoto.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
        txtName.setEnabled(!txtName.isEnabled());
        txtUsername.setEnabled(!txtUsername.isEnabled());
        txtEmail.setEnabled(!txtEmail.isEnabled());
        btnSubmitChanges.setVisibility((btnSubmitChanges.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
    }

    public void logout(View view) {
        accountRepo.deleteTokenUser(Utils.getToken());

//        Preferences.providePreferences().edit().clear().apply();
        Preferences.providePreferences().edit().putString("token", "").apply();
        Preferences.providePreferences().edit().putString("account" ,new Gson().toJson(new AccountProfile())).apply();

        super.isLogged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).error(R.drawable.ic_launcher_background).placeholder(R.drawable.progress_animation).resize(350, 350).into(ivProfilePhoto);
            File file = new File(getRealPathFromURI(imageUri,this));

            RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/*"));
            MultipartBody.Part body2 = MultipartBody.Part.createFormData("image_file", file.getName(), requestFile);

            Log.d(TAG, "updatePhoto -> he rebut la uri: " + imageUri.toString());
            AccountProfile accountProfile = new Gson().fromJson(Preferences.providePreferences().getString("account", ""), AccountProfile.class);

            Log.d(TAG, "updatePhoto -> he rebut la url: " + accountProfile.getPhoto());

            accountRepo.updatePhoto(body2, Utils.getToken());
            accountRepo.get_account(Utils.getToken());
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
        if( ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            permisoDeAlmacenamientoConcedido();
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, PICK_IMAGE);
        }
        else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    EXTERNAL_STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case EXTERNAL_STORAGE_PERMISSION_CODE:
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

    private void permisoDeAlmacenamientoConcedido() {
        Toast.makeText(this, "El permiso para el almacenamiento está concedido", Toast.LENGTH_SHORT).show();

    }

    private void permisoDeAlmacenamientoNoConcedido() {
        Toast.makeText(this, "El permiso para el almacenamiento no está concedido", Toast.LENGTH_SHORT).show();
    }
}