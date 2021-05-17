package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import dam.invisere.gtidic.udl.cat.invisereapp.models.AccountProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.models.PublicProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;

public class PublicProfileActivityViewModel extends ViewModel {

    private AccountRepo accountRepo;
    private PublicProfile accountPublicProfile = new Gson().fromJson(Preferences.providePreferences().getString("publicAccount", ""), PublicProfile.class);

    public MutableLiveData<String> Name = new MutableLiveData<>();
    public MutableLiveData<String> Username = new MutableLiveData<>();
    public MutableLiveData<String> Photo = new MutableLiveData<>();
    public MutableLiveData<String> NumeroRutes = new MutableLiveData<>();


    public PublicProfileActivityViewModel(){
        this.accountRepo = new AccountRepo();
        Name.setValue(accountPublicProfile.getName());
        Username.setValue(accountPublicProfile.getUsername());
        NumeroRutes.setValue(accountPublicProfile.getRutes());

        if(accountPublicProfile.getPhoto() != null){
            Photo.setValue(accountPublicProfile.getPhoto().replace("http://127.0.0.1:8001","http://192.168.101.88:8001"));
        } else {
            Photo.setValue("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/480px-No_image_available.svg.png");
        }
    }

}
