package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.models.AccountProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.Utils;

public class PrivateProfileActivityViewModel extends ViewModel {

    public AccountRepo accountRepo;
    private AccountProfile accountProfile = Utils.getAccountProfile();

    public MutableLiveData<String> Name = new MutableLiveData<>();
    public MutableLiveData<String> Username = new MutableLiveData<>();
    public MutableLiveData<String> Email = new MutableLiveData<>();
    public MutableLiveData<String> Photo = new MutableLiveData<>();

    public PrivateProfileActivityViewModel(){
        this.accountRepo = new AccountRepo();
        Name.setValue(accountProfile.getName());
        Username.setValue(accountProfile.getUsername());
        Email.setValue(accountProfile.getEmail());
        if(accountProfile.getPhoto() != null){
            Photo.setValue(accountProfile.getPhoto().replace("http://127.0.0.1:8001","http://192.168.101.88:8001"));
        } else {
            Photo.setValue("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/480px-No_image_available.svg.png");
        }
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

}
