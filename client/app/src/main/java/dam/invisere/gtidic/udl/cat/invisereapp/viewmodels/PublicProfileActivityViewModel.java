package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import dam.invisere.gtidic.udl.cat.invisereapp.models.PublicProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Route;
import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;
import dam.invisere.gtidic.udl.cat.invisereapp.validators.ReturnCodeI;

import static android.content.ContentValues.TAG;

public class PublicProfileActivityViewModel extends ViewModel {

    private AccountRepo accountRepo;

    public MutableLiveData<String> Name = new MutableLiveData<>();
    public MutableLiveData<String> Username = new MutableLiveData<>();
    public MutableLiveData<String> Photo = new MutableLiveData<>();
    public MutableLiveData<String> NumeroRutes = new MutableLiveData<>();
    public int numeroRutesInteger;
    public PublicProfile accountPublicProfile;

    public PublicProfileActivityViewModel(){
        this.accountRepo = new AccountRepo();

        accountPublicProfile = new Gson().fromJson(Preferences.providePreferences().getString("publicAccount", ""), PublicProfile.class);
        Username.setValue(accountPublicProfile.getUsername());
        Name.setValue(accountPublicProfile.getName());
        Photo.setValue(accountPublicProfile.getPhoto());

        Log.d(TAG, "PublicAccount Name: " + accountPublicProfile.getName());
        Log.d(TAG, "PublicAccount username: " + accountPublicProfile.getUsername());
        Log.d(TAG, "PublicAccount photo: " + accountPublicProfile.getPhoto());
        Log.d(TAG, "PublicAccount Rutes: " + accountPublicProfile.getRutes().length);

        if(accountPublicProfile.getRutes() != null){
            numeroRutesInteger = accountPublicProfile.getRutes().length;
            NumeroRutes.setValue("" + numeroRutesInteger);
        }
        else{
            NumeroRutes.setValue("0");
            Log.d(TAG, "PublicAccount Rutes: " + NumeroRutes.getValue());
        }

        if(accountPublicProfile.getPhoto() != null){
            Photo.setValue(accountPublicProfile.getPhoto().replace("http://127.0.0.1:8001","http://192.168.101.88:8001"));
            Log.d(TAG, "Replace Photo: " + Photo.getValue());
        } else {
            Photo.setValue("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/480px-No_image_available.svg.png");
        }
    }

}
