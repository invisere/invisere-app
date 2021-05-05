package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import dam.invisere.gtidic.udl.cat.invisereapp.PrivateProfileActivity;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.models.AccountProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;

public class PrivateProfileActivityViewModel extends ViewModel {

    private AccountRepo accountRepo;
    private AccountProfile accountProfile = new Gson().fromJson(Preferences.providePreferences().getString("account", ""), AccountProfile.class);
    private PrivateProfileActivity activity;

    public MutableLiveData<String> Name = new MutableLiveData<>();
    public MutableLiveData<String> Username = new MutableLiveData<>();
    public MutableLiveData<String> Email = new MutableLiveData<>();
    public MutableLiveData<String> Photo = new MutableLiveData<>();

    public PrivateProfileActivityViewModel(PrivateProfileActivity activity){
        this.accountRepo = new AccountRepo();
        this.activity = activity;
        Name.setValue(accountProfile.getName());
        Username.setValue(accountProfile.getUsername());
        Email.setValue(accountProfile.getEmail());
        if(accountProfile.getPhoto() != null){
            Photo.setValue(accountProfile.getPhoto().replace("http://127.0.0.1:8001","http://192.168.101.88:8001"));
        } else {
            Photo.setValue("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/480px-No_image_available.svg.png");
        }
    }

    public void onBack() {
        activity.finish();
    }

    public void onEdit() {
        activity.toggle();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateProfile() {
        Account account = new Account();
        account.setName((Name.getValue().equals(accountProfile.getName())) ? null : Name.getValue());
        account.setUsername((Username.getValue().equals(accountProfile.getUsername())) ? null : Username.getValue());
        account.setEmail((Email.getValue().equals(accountProfile.getEmail())) ? null : Email.getValue());
        account.setPassword(null);

        if(account.getName() != null || account.getUsername() != null || account.getEmail() != null) {
            accountRepo.updateAccount(account);
        }
    }



    private void permisoDeAlmacenamientoConcedido() {
        Toast.makeText(activity.getContext(), "El permiso para el almacenamiento está concedido", Toast.LENGTH_SHORT).show();
    }

    private void permisoDeAlmacenamientoNoConcedido() {
        Toast.makeText(activity.getContext(), "El permiso para el almacenamiento no está concedido", Toast.LENGTH_SHORT).show();
    }

}
