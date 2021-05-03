package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.EULA;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.Utils;
import dam.invisere.gtidic.udl.cat.invisereapp.validators.AccountValidator;
import dam.invisere.gtidic.udl.cat.invisereapp.validators.ValidationResultImpl;

public class SignUpViewModel extends ViewModel {

    public AccountRepo accountRepo;

    public MutableLiveData<String> Name = new MutableLiveData<>();
    public MutableLiveData<ValidationResultImpl> NameValidation = new MutableLiveData<>();
    public MutableLiveData<String> Username = new MutableLiveData<>();
    public MutableLiveData<ValidationResultImpl> UsernameValidation = new MutableLiveData<>();
    public MutableLiveData<String> Email = new MutableLiveData<>();
    public MutableLiveData<ValidationResultImpl> EmailValidation = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<ValidationResultImpl> PasswordValidation = new MutableLiveData<>();
    public MutableLiveData<Boolean> EULA = new MutableLiveData<>();
    public MutableLiveData<ValidationResultImpl> EULAValidation = new MutableLiveData<>();

    public SignUpViewModel() {
        this.accountRepo = new AccountRepo();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onRegister() {
        if(isFormValid()) {
            Account account = new Account();
            account.setName(Name.getValue());
            account.setUsername(Username.getValue());
            account.setEmail(Email.getValue());
            account.setPassword(Password.getValue());
            Log.d("signUpVM", account.toString());
            this.accountRepo.registerAccount(account);
        }
    }

    public void onCheckEULA(View view) {
        new EULA(EULA).show(((AppCompatActivity) view.getContext()).getSupportFragmentManager(), "EULAConfirmationDialog");
    }

    public boolean isFormValid(){
        NameValidation.setValue(AccountValidator.checkName(Name.getValue()));
        UsernameValidation.setValue(AccountValidator.checkUsername(Username.getValue()));
        EmailValidation.setValue(AccountValidator.checkEmail(Email.getValue()));
        PasswordValidation.setValue(AccountValidator.checkPassword(Password.getValue()));
        EULAValidation.setValue(AccountValidator.checkEULA(EULA.getValue()));

        return NameValidation.getValue().isSuccess() && UsernameValidation.getValue().isSuccess() && EmailValidation.getValue().isSuccess() && PasswordValidation.getValue().isSuccess() && EULAValidation.getValue().isSuccess();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createToken(){
        String auth = Utils.createAuth(Username.getValue(), Password.getValue());
        this.accountRepo.createTokenUser(auth);
    }

}
