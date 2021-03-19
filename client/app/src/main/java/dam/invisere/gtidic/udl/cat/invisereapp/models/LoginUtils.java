package dam.invisere.gtidic.udl.cat.invisereapp.models;
import android.content.Context;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.jar.Attributes;
import java.util.regex.Pattern;

import dam.invisere.gtidic.udl.cat.invisereapp.R;
import dam.invisere.gtidic.udl.cat.invisereapp.RegisterActivity;


public class LoginUtils extends RegisterActivity {

    static String EmailRegex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    static String NameSurnameRegex = "(^([a-zñáéíóúA-ZÁÉÍÓÚ]+){2,15})(\\s[A-ZÁÉÍÓÚ]{1}([a-zñáéíóú]+){2,15})?$";

    static String PasswordRegex = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

    static String SpecialCharactersRegex = "^(?=.*[$%&|<>#!@?¿=()/ºª#+-.-_,;]).*$";

    static String NumbersRegex = "^(?=.*[0-9]).*$";

    public static boolean checkPassword(String password, String confirmPassword, TextInputLayout textPassword, TextInputLayout textConfirmPassword){

        if(password.matches(PasswordRegex)){
            textPassword.setErrorEnabled(false);
            if(!password.equals(confirmPassword)){
                textConfirmPassword.setError(getContext().getResources().getString(R.string.Message_error_passwords_not_equals));
                return false;
            }
            else{
                textPassword.setErrorEnabled(false);
                textConfirmPassword.setErrorEnabled(false);
                return true;
            }

        }
        else{
            messageErrorPassword(password, textPassword);

            return false;
        }
    }

    public static void messageErrorPassword(String password, TextInputLayout TextPassword){

            if(!password.matches("^(?=.*[A-Z]).*$")) {
                TextPassword.setError(getContext().getResources().getString(R.string.Message_error_password_does_not_contain_capital_letters));
            }
            else if(!password.matches(NumbersRegex)){
                TextPassword.setError(getContext().getResources().getString(R.string.Message_error_password_does_not_contain_number));
            }
            else if(!password.matches(SpecialCharactersRegex)){
                TextPassword.setError(getContext().getResources().getString(R.string.Message_error_password_does_not_contain_special_characters));
            }
            else {
                TextPassword.setError(getContext().getResources().getString(R.string.Message_error_password_has_invalid_length));
            }
    }

    public static boolean checkName(String name, TextInputLayout textName){

        if(name.matches(NameSurnameRegex)){
            textName.setErrorEnabled(false);
            return true;
        }
        else{
            messageErrorName(name, textName);
            return false;
        }
    }

    public static void messageErrorName(String name, TextInputLayout textName){

        if(name.equals("")){
            textName.setError(getContext().getResources().getString(R.string.Message_error_name_empty));
        }

        else if(name.matches(SpecialCharactersRegex)){
            textName.setError(getContext().getResources().getString(R.string.Message_error_name_cannot_contain_special_characters));
        }

        else if(name.matches(NumbersRegex)){
            textName.setError(getContext().getResources().getString(R.string.Message_error_name_cannot_contain_number));
        }
        else {
            textName.setError(getContext().getResources().getString(R.string.Message_error_name_has_invalid_length));
        }
    }

    public static boolean checkSurname(String surname, TextInputLayout textSurname){

        if(surname.matches(NameSurnameRegex)){
            textSurname.setErrorEnabled(false);
            return true;
        }
        else{
            messageErrorSurname(surname, textSurname);
            return false;
        }
    }

    public static void messageErrorSurname(String surname, TextInputLayout textSurname){

        if(surname.equals("")){
            textSurname.setError(getContext().getResources().getString(R.string.Message_error_surname_empty));
        }
        else if(surname.matches(SpecialCharactersRegex)){
            textSurname.setError(getContext().getResources().getString(R.string.Message_error_surname_cannot_contain_special_characters));
        }
        else if(surname.matches(NumbersRegex)){
            textSurname.setError(getContext().getResources().getString(R.string.Message_error_name_cannot_contain_number));
        }
        else {
            textSurname.setError(getContext().getResources().getString(R.string.Message_error_surname_has_invalid_length));
        }
    }

    public static boolean isValidEmailAddress(String email, TextInputLayout textEmail){

        if(email.equals("")){
            textEmail.setError(getContext().getResources().getString(R.string.Message_error_email_empty));
            return false;
        }

        else if(!email.matches(EmailRegex)){
            textEmail.setError(getContext().getResources().getString(R.string.Message_error_email_invalid));
            return false;
        }
        else{
            textEmail.setErrorEnabled(false);
            return true;
        }
    }

}
