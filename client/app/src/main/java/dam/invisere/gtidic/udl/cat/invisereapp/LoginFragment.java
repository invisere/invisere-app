package dam.invisere.gtidic.udl.cat.invisereapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import dam.invisere.gtidic.udl.cat.invisereapp.databinding.FragmentLoginBinding;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.Utils;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.LoginViewModel;

public class LoginFragment extends Fragment {

    private Button buttonSignup;
    private LoginViewModel loginViewModel;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        FragmentLoginBinding fragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        View view = fragmentLoginBinding.getRoot();
        fragmentLoginBinding.setLifecycleOwner(this);
        fragmentLoginBinding.setViewModel(loginViewModel);

        buttonSignup = view.findViewById(R.id.button_login_to_signup);

        buttonSignup.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signupFragment));

        loginViewModel.getLoginResponse().observe(getViewLifecycleOwner(), returnCode -> {
            if(returnCode.getReturnCode() == 200) {
                loginViewModel.accountRepo.get_account(Utils.getToken());
            }
        });

        loginViewModel.getAccountResponse().observe(getViewLifecycleOwner(), returnCodeI -> {
            if(returnCodeI.getReturnCode() == 200) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });

        return view;
    }
}