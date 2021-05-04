package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import dam.invisere.gtidic.udl.cat.invisereapp.models.AccountProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;

public class PrivateProfileActivityViewModel extends ViewModel {

    private AccountProfile accountProfile = new AccountProfile();

    public MutableLiveData<String> Name = new MutableLiveData<>();
    public MutableLiveData<String> Username = new MutableLiveData<>();
    public MutableLiveData<String> Email = new MutableLiveData<>();
    public MutableLiveData<String> Photo = new MutableLiveData<>();

    public PrivateProfileActivityViewModel(){
        accountProfile = new Gson().fromJson(Preferences.providePreferences().getString("account", ""), AccountProfile.class);
        Name.setValue(accountProfile.getName());
        Username.setValue(accountProfile.getUsername());
        Email.setValue(accountProfile.getEmail());
        if(accountProfile.getPhoto() != null){
            Photo.setValue(accountProfile.getPhoto());
        } else {
//            Photo.setValue("https://previews.123rf.com/images/boxerx/boxerx1611/boxerx161100006/68882648-descargar-signo-en-fondo-blanco-cargar-icono-barra-de-carga-de-datos-ilustraci%C3%B3n-de-stock-vector.jpg");
            Photo.setValue("https://www.digitallearning.es/wp-content/uploads/2017/03/Test_cuadrado.jpg");
        }
    }

    public void getAcc(AccountProfile accountProfile){
        this.accountProfile = accountProfile;
        Name.setValue(accountProfile.getName());
        Username.setValue(accountProfile.getUsername());
        Email.setValue(accountProfile.getEmail());
        Photo.setValue(accountProfile.getPhoto());
    }

//    public static void updateFields(AccountProfile accountProfile){
//
//        Name.setValue(accountProfile.getName());
//        Username.setValue(accountProfile.getUsername());
//        Email.setValue(accountProfile.getEmail());
//        Photo.setValue(accountProfile.getPhoto());
//
//        Log.d(TAG, "Name: " + Name.getValue());
//        Log.d(TAG, "Username: " + Username.getValue());
//        Log.d(TAG, "Email: " + Email.getValue());
//        Log.d(TAG, "UrlPhoto: " + Photo.getValue());
//
//        if(Photo.getValue() != null){
//            Photo.setValue(Photo.getValue().replace("http://127.0.0.1:8001","http://192.168.101.88:8001"));
//            Log.d(TAG, "UrlPhoto2: " + Photo.getValue());
//
//            //Pensar com fer això amb el mvvm
//            //Picasso.get().load(Photo.getValue()).error(R.drawable.ic_launcher_background).resize(350, 350).into(photoImage);
//        }
//        else {
//            //Picasso.get().load("https://previews.123rf.com/images/boxerx/boxerx1611/boxerx161100006/68882648-descargar-signo-en-fondo-blanco-cargar-icono-barra-de-carga-de-datos-ilustraci%C3%B3n-de-stock-vector.jpg").error(R.drawable.ic_launcher_background).resize(350, 350).into(photoImage);
//        }
//    }



}
