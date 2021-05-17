package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import dam.invisere.gtidic.udl.cat.invisereapp.databinding.ActivityPrivateProfileBinding;
import dam.invisere.gtidic.udl.cat.invisereapp.models.AccountProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.models.PublicProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.Utils;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.PrivateProfileActivityViewModel;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.PublicProfileActivityViewModel;

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

    public String username2;

    private Button btn;

    private String TAG = "PrivatePublicProfileActivity()";
    private PublicProfileActivityViewModel publicProfileActivityViewModel;

    private AccountRepo accountRepo;

    public PublicProfileActivity() {
        this.accountRepo = new AccountRepo();
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

        accountRepo.get_public_account(Utils.getToken(),"admin");

    }

    private void initViewModel() {
        publicProfileActivityViewModel = new PublicProfileActivityViewModel();
        ActivityPrivateProfileBinding activityPrivateProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_user);
        activityPrivateProfileBinding.setLifecycleOwner(this);
        activityPrivateProfileBinding.setViewModel(publicProfileActivityViewModel);
    }

    public void onBack(View view) {
        finish();
    }



}