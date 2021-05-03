package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dam.invisere.gtidic.udl.cat.invisereapp.models.AccountProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;

import static android.content.ContentValues.TAG;

public class ProfileActivityViewModel extends ViewModel {

    private AccountRepo accountRepo;
    private String token = Preferences.providePreferences().getString("token", "");

    public static MutableLiveData<String> Name;
    public static MutableLiveData<String> Username;
    public static MutableLiveData<String> Email;
    public static MutableLiveData<String> Photo;

    public ProfileActivityViewModel(){
        this.Name = new MutableLiveData<>();
        this.Username = new MutableLiveData<>();
        this.Email = new MutableLiveData<>();
        this.Photo = new MutableLiveData<>();
        this.accountRepo = new AccountRepo();
        this.accountRepo.get_account(token);
    }

    public static void updateFields(AccountProfile accountProfile){

        Name.setValue(accountProfile.getName());
        Username.setValue(accountProfile.getUsername());
        Email.setValue(accountProfile.getEmail());
        Photo.setValue(accountProfile.getPhoto());

        Log.d(TAG, "Name: " + Name.getValue());
        Log.d(TAG, "Username: " + Username.getValue());
        Log.d(TAG, "Email: " + Email.getValue());
        Log.d(TAG, "UrlPhoto: " + Photo.getValue());

        if(Photo.getValue() != null){
            Photo.setValue(Photo.getValue().replace("http://127.0.0.1:8001","http://192.168.101.88:8001"));
            Log.d(TAG, "UrlPhoto2: " + Photo.getValue());

            //Pensar com fer això amb el mvvm
            //Picasso.get().load(Photo.getValue()).error(R.drawable.ic_launcher_background).resize(350, 350).into(photoImage);
        }
        else {
            //Picasso.get().load("https://previews.123rf.com/images/boxerx/boxerx1611/boxerx161100006/68882648-descargar-signo-en-fondo-blanco-cargar-icono-barra-de-carga-de-datos-ilustraci%C3%B3n-de-stock-vector.jpg").error(R.drawable.ic_launcher_background).resize(350, 350).into(photoImage);
        }
    }

}
