package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import dam.invisere.gtidic.udl.cat.invisereapp.databinding.ActivityPrivateProfileBinding;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.PrivateProfileActivityViewModel;

public class PrivateProfileActivity extends AppCompatActivity {

    private PrivateProfileActivityViewModel privateProfileActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_profile);
        initViewModel();

        ShapeableImageView photoImage = findViewById(R.id.photoProfile);
        privateProfileActivityViewModel.Photo.observe(this, s -> {
            Picasso.get().load(s).error(R.drawable.ic_launcher_background).resize(350, 350).into((ImageView) findViewById(R.id.photoProfile));
        });
    }

    private void initViewModel() {
        privateProfileActivityViewModel = new PrivateProfileActivityViewModel();
        ActivityPrivateProfileBinding activityPrivateProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_private_profile);
        activityPrivateProfileBinding.setLifecycleOwner(this);
        activityPrivateProfileBinding.setViewModel(privateProfileActivityViewModel);
    }
}