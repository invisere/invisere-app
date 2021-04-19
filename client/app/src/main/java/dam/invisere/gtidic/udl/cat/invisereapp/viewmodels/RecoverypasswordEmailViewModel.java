package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import dam.invisere.gtidic.udl.cat.invisereapp.RecoverypasswordEmailFragmentDirections;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;

public class RecoverypasswordEmailViewModel extends ViewModel {

    private final String TAG = "RpassEmailViewModel";
    private final AccountRepo accountRepo;

    public MutableLiveData<String> Email = new MutableLiveData<>();

    public RecoverypasswordEmailViewModel() {
        accountRepo = new AccountRepo();
    }

    public void onConfirm(View view) {
        Log.d(TAG, "onConfirm() -> " + Email.getValue());
        if(Email.getValue() != null) {
            accountRepo.recovery(Email.getValue());
            RecoverypasswordEmailFragmentDirections.ActionRecoverypasswordEmailFragmentToRecoverypasswordCodeFragment action = RecoverypasswordEmailFragmentDirections.actionRecoverypasswordEmailFragmentToRecoverypasswordCodeFragment(Email.getValue());
            Navigation.findNavController(view).navigate(action);
        }
    }
}
