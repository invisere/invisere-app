package dam.invisere.gtidic.udl.cat.invisereapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;

import dam.invisere.gtidic.udl.cat.invisereapp.databinding.FragmentSignupBinding;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.SignUpViewModel;

public class SignupFragment extends Fragment {

    private Button buttonLogin;
    private TextInputLayout textName;
    private TextInputLayout textUsername;
    private TextInputLayout textEmail;
    private TextInputLayout textPassword;
    private CheckBox checkBoxEula;
    private SignUpViewModel signUpViewModel;

    public SignupFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        FragmentSignupBinding fragmentSignupBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false);
        View view = fragmentSignupBinding.getRoot();
        fragmentSignupBinding.setLifecycleOwner(this);
        fragmentSignupBinding.setViewModel(signUpViewModel);

        buttonLogin = view.findViewById(R.id.button_signup_to_login);
        textName = view.findViewById(R.id.TextField_name_register);
        textUsername = view.findViewById(R.id.TextField_username_register);
        textEmail = view.findViewById(R.id.TextField_email_register);
        textPassword = view.findViewById(R.id.TextField_password_register);
        checkBoxEula = view.findViewById(R.id.checkbox_eula);

        signUpViewModel.NameValidation.observe(getViewLifecycleOwner(), validationResult -> {
            if(!validationResult.isSuccess()){
                textName.setError(getContext().getResources().getString(validationResult.getMessage()));
            }else {
                textName.setErrorEnabled(false);
            }
        });

        signUpViewModel.UsernameValidation.observe(getViewLifecycleOwner(), validationResult -> {
            if(!validationResult.isSuccess()){
                textUsername.setError(getContext().getResources().getString(validationResult.getMessage()));
            }else {
                textUsername.setErrorEnabled(false);
            }
        });

        signUpViewModel.EmailValidation.observe(getViewLifecycleOwner(), validationResult -> {
            if(!validationResult.isSuccess()){
                textEmail.setError(getContext().getResources().getString(validationResult.getMessage()));
            }else {
                textEmail.setErrorEnabled(false);
            }
        });

        signUpViewModel.PasswordValidation.observe(getViewLifecycleOwner(), validationResult -> {
            if(!validationResult.isSuccess()){
                textPassword.setError(getContext().getResources().getString(validationResult.getMessage()));
            }else {
                textPassword.setErrorEnabled(false);
            }
        });

        signUpViewModel.EULAValidation.observe(getViewLifecycleOwner(), validationResult -> {
            if(!validationResult.isSuccess()){
                checkBoxEula.setError(getContext().getResources().getString(validationResult.getMessage()));
            }else {
                checkBoxEula.setError(null);
            }
        });

        signUpViewModel.getSignUpResponse().observe(getViewLifecycleOwner(), returnCodeI -> {
            if(returnCodeI.getReturnCode() == 200) {
                signUpViewModel.createToken();
            }
        });

        signUpViewModel.getCreateTokenResponse().observe(getViewLifecycleOwner(), returnCodeI -> {
            if(returnCodeI.getReturnCode() == 200) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });

        textName.setErrorEnabled(true);
        textUsername.setErrorEnabled(true);
        textEmail.setErrorEnabled(true);
        textPassword.setErrorEnabled(true);

        buttonLogin.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_loginFragment));

        return view;
    }
}