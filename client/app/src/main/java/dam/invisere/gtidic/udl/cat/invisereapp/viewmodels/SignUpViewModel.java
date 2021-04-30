package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.Utils;
import dam.invisere.gtidic.udl.cat.invisereapp.validators.AccountValidator;
import dam.invisere.gtidic.udl.cat.invisereapp.validators.ReturnCodeI;
import dam.invisere.gtidic.udl.cat.invisereapp.validators.ValidationResultImpl;

public class SignUpViewModel extends ViewModel {

    public AccountRepo accountRepo;

    public MutableLiveData<String> Name = new MutableLiveData<>();
    public MutableLiveData<ValidationResultImpl> NameValidaton = new MutableLiveData<>();
    public MutableLiveData<String> Username = new MutableLiveData<>();
    public MutableLiveData<ValidationResultImpl> UsernameValidaton = new MutableLiveData<>();
    public MutableLiveData<String> Email = new MutableLiveData<>();
    public MutableLiveData<ValidationResultImpl> EmailValidaton = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<ValidationResultImpl> PasswordValidaton = new MutableLiveData<>();

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

    //Aixo hauria de ser la Interficie.
    public MutableLiveData<ReturnCodeI> getSignUpResponse(){
        return accountRepo.mResponseRegister;
    }

    public MutableLiveData<ReturnCodeI> getCreateTokenResponse(){
        return accountRepo.mResponseLogin;
    }

    public boolean isFormValid(){
        NameValidaton.setValue(AccountValidator.checkName(Name.getValue()));
        UsernameValidaton.setValue(AccountValidator.checkUsername(Username.getValue()));
        EmailValidaton.setValue(AccountValidator.checkEmail(Email.getValue()));
        PasswordValidaton.setValue(AccountValidator.checkPassword(Password.getValue()));

        if(NameValidaton.getValue().isSuccess() && UsernameValidaton.getValue().isSuccess() && EmailValidaton.getValue().isSuccess() && PasswordValidaton.getValue().isSuccess())
            return true;
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createToken(){
        String auth = Utils.createAuth(Username.getValue(), Password.getValue());
        this.accountRepo.createTokenUser(auth);
    }

}
