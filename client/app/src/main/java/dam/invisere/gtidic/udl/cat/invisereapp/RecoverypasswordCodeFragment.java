package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;

import dam.invisere.gtidic.udl.cat.invisereapp.databinding.FragmentRecoverypasswordCodeBinding;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.RecoverypasswordCodeViewModel;

public class RecoverypasswordCodeFragment extends Fragment {

    private RecoverypasswordCodeViewModel recoverypasswordCodeViewModel;

    public RecoverypasswordCodeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recoverypasswordCodeViewModel = new ViewModelProvider(this).get(RecoverypasswordCodeViewModel.class);
        FragmentRecoverypasswordCodeBinding fragmentRecoverypasswordCodeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recoverypassword_code, container, false);
        View view = fragmentRecoverypasswordCodeBinding.getRoot();
        fragmentRecoverypasswordCodeBinding.setLifecycleOwner(this);
        fragmentRecoverypasswordCodeBinding.setViewModel(recoverypasswordCodeViewModel);

        recoverypasswordCodeViewModel.Email.setValue(RecoverypasswordCodeFragmentArgs.fromBundle(getArguments()).getEmail());

        TextInputLayout passwordTextInput = view.findViewById(R.id.TextField_password_recoveryCode);
        recoverypasswordCodeViewModel.PasswordValidaton.observe(getViewLifecycleOwner(), validationResult -> {
            if(!validationResult.isSuccess()){
                passwordTextInput.setError(getContext().getResources().getString(validationResult.getMessage()));
            }else {
                passwordTextInput.setErrorEnabled(false);
            }
        });

        return view;
    }
}