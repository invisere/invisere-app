package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;

import dam.invisere.gtidic.udl.cat.invisereapp.databinding.FragmentLoginBinding;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.LoginViewModel;

public class LoginFragment extends Fragment {

    private Button buttonSignup;
    private TextInputLayout textUsername;
    private TextInputLayout textPassword;
    private Button buttonLogin;
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

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        FragmentLoginBinding fragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        View view = fragmentLoginBinding.getRoot();
        fragmentLoginBinding.setLifecycleOwner(this);
        fragmentLoginBinding.setViewModel(loginViewModel);

        textUsername.setErrorEnabled(true);
        textPassword.setErrorEnabled(true);

        buttonSignup.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signupFragment));

        return view;
    }
}