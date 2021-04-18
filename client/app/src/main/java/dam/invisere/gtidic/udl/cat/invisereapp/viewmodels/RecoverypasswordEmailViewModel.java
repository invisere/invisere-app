package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import dam.invisere.gtidic.udl.cat.invisereapp.R;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;

public class RecoverypasswordEmailViewModel extends ViewModel {

    private final String TAG = "RpassEmailViewModel";
    private final AccountRepo accountRepo;

    public MutableLiveData<String> Email = new MutableLiveData<>();

    public RecoverypasswordEmailViewModel() {
        accountRepo = new AccountRepo();
    }

    public void onConfirm(View view) {
        Log.d(TAG, "onConfirm()");
        Account account = new Account();
        account.setEmail(Email.getValue());
        accountRepo.recovery(account);
        Navigation.findNavController(view).navigate(R.id.action_recoverypasswordEmailFragment_to_recoverypasswordCodeFragment);
    }
}
