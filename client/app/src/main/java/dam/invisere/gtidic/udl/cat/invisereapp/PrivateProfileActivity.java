package dam.invisere.gtidic.udl.cat.invisereapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import dam.invisere.gtidic.udl.cat.invisereapp.databinding.ActivityPrivateProfileBinding;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.PrivateProfileActivityViewModel;

public class PrivateProfileActivity extends AppCompatActivity {

    private String TAG = "PrivateProfileActivity()";
    private PrivateProfileActivityViewModel privateProfileActivityViewModel;

    private Button btnChangePhoto;
    private TextInputLayout txtName;
    private TextInputLayout txtUsername;
    private TextInputLayout txtEmail;
    private Button btnSubmitChanges;

    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_profile);
        initViewModel();
        btnChangePhoto = findViewById(R.id.buttonChangePhoto);
        txtName = findViewById(R.id.TextField_name_profile);
        txtUsername = findViewById(R.id.TextField_username_profile);
        txtEmail = findViewById(R.id.TextField_email_profile);
        btnSubmitChanges = findViewById(R.id.buttonSubmitChanges);
        toggle();

        privateProfileActivityViewModel.Photo.observe(this, s -> Picasso.get().load(s).error(R.drawable.ic_launcher_background).placeholder(R.drawable.progress_animation).resize(350, 350).into((ImageView) findViewById(R.id.photoProfile)));
    }

    private void initViewModel() {
        privateProfileActivityViewModel = new PrivateProfileActivityViewModel(this);
        ActivityPrivateProfileBinding activityPrivateProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_private_profile);
        activityPrivateProfileBinding.setLifecycleOwner(this);
        activityPrivateProfileBinding.setViewModel(privateProfileActivityViewModel);
    }

    public void toggle() {
        btnChangePhoto.setVisibility((btnChangePhoto.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
        txtName.setEnabled(!txtName.isEnabled());
        txtUsername.setEnabled(!txtUsername.isEnabled());
        txtEmail.setEnabled(!txtEmail.isEnabled());
        btnSubmitChanges.setVisibility((btnSubmitChanges.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
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