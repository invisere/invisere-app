package dam.invisere.gtidic.udl.cat.invisereapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import dam.invisere.gtidic.udl.cat.invisereapp.databinding.NavHeaderMainBinding;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.Utils;

public class MainActivity extends AuthActivity{

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_InvisereApp_NoActionBar);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        NavHeaderMainBinding navHeaderMainBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.nav_header_main,navigationView,true);
        navHeaderMainBinding.setAccount(Utils.getAccountProfile());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_rutes, R.id.nav_punts, R.id.nav_gimcanes, R.id.nav_escapes)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        if(Utils.getAccountProfile().getPhoto().isEmpty()) {
            Picasso.get().load(R.drawable.ic_launcher_background).placeholder(R.drawable.progress_animation).resize(200, 200).into((ImageView) findViewById(R.id.imageView));
        } else {
            Picasso.get().load(Utils.getAccountProfile().getPhoto().replace("http://127.0.0.1:8001","http://192.168.101.88:8001")).error(R.drawable.ic_launcher_background).placeholder(R.drawable.progress_animation).resize(200, 200).into((ImageView) findViewById(R.id.imageView));
        }
        LinearLayout layout = findViewById(R.id.linearLayoutHeader);
        layout.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PrivateProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplication().startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}