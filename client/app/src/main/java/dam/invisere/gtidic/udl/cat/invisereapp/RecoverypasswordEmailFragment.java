package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dam.invisere.gtidic.udl.cat.invisereapp.databinding.FragmentRecoverypasswordEmailBinding;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.RecoverypasswordEmailViewModel;

public class RecoverypasswordEmailFragment extends Fragment {

    private RecoverypasswordEmailViewModel recoverypasswordEmailViewModel;

    public RecoverypasswordEmailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recoverypasswordEmailViewModel = new ViewModelProvider(this).get(RecoverypasswordEmailViewModel.class);
        FragmentRecoverypasswordEmailBinding fragmentRecoverypasswordEmailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recoverypassword_email, container, false);
        View view = fragmentRecoverypasswordEmailBinding.getRoot();
        fragmentRecoverypasswordEmailBinding.setLifecycleOwner(this);
        fragmentRecoverypasswordEmailBinding.setViewModel(recoverypasswordEmailViewModel);

        return view;
    }
}