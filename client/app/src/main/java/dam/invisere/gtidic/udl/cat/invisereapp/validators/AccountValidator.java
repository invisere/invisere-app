package dam.invisere.gtidic.udl.cat.invisereapp.validators;

import android.widget.CheckBox;

import com.google.android.material.textfield.TextInputLayout;

import dam.invisere.gtidic.udl.cat.invisereapp.EntryActivity;
import dam.invisere.gtidic.udl.cat.invisereapp.R;


public class AccountValidator extends EntryActivity {

    static String EmailRegex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]{1,3}$";

    static String NameUsernameRegex = "^[a-zñáéíóúñA-ZÁÉÍÓÚÑ]+[\\s|-]?[a-zñáéíóúñA-ZÁÉÍÓÚÑ]+[\\s|-]?[a-zñáéíóúñA-ZÁÉÍÓÚÑ]+$";

    static String PasswordRegex = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[$%&|<>#!@?¿=^()€/ºª#+-.-_,;])(?=\\S+$).{8,}$";

    static String SpecialCharactersRegex = "^(?=.*[$%&|<>#!@?¿=^()€/ºª#+-.-_,;]).*$";

    static String NumbersRegex = "^(?=.*[0-9]).*$";

    /** @TODO: D'aquesta manera podem testejar de forma unitaria  la lògica,
     *  eliminem la dependecia del TextInputView. Repetir amb la resta.
     */

    public static ValidationResultImpl checkName(String name){
        String msg = "";
        boolean success = true;
        if(!name.matches(NameUsernameRegex)){
            msg = messageErrorName(name);
            success = false;
        }
        return new ValidationResultImpl(msg,success);
    }

    public static String messageErrorName(String name){

        if(name.equals("")){
            return (getContext().getResources().getString(R.string.Message_error_name_empty));
        }

        else if(name.matches(SpecialCharactersRegex)){
            return (getContext().getResources().getString(R.string.Message_error_name_cannot_contain_special_characters));
        }

        else if(name.matches(NumbersRegex)){
            return (getContext().getResources().getString(R.string.Message_error_name_cannot_contain_number));
        }
        else {
            return (getContext().getResources().getString(R.string.Message_error_name_has_invalid_length));
        }
    }

    public static boolean checkUsername(String username, TextInputLayout textusername){

        if(username.matches(NameUsernameRegex)){
            if(textusername != null)
                textusername.setErrorEnabled(false);
            return true;
        }
        else{
            if(textusername != null)
                messageErrorusername(username, textusername);
            return false;
        }
    }

    //@TODO: Aquest codi esta duplicat
    public static void messageErrorusername(String username, TextInputLayout textusername){

        if(username.equals("")){
            textusername.setError(getContext().getResources().getString(R.string.Message_error_username_empty));
        }
        else if(username.matches(SpecialCharactersRegex)){
            textusername.setError(getContext().getResources().getString(R.string.Message_error_username_cannot_contain_special_characters));
        }
        else if(username.matches(NumbersRegex)){
            textusername.setError(getContext().getResources().getString(R.string.Message_error_name_cannot_contain_number));
        }
        else {
            textusername.setError(getContext().getResources().getString(R.string.Message_error_username_has_invalid_length));
        }
    }

    public static boolean isValidEmailAddress(String email, TextInputLayout textEmail){

        if(email.equals("")){
            if(textEmail != null)
                textEmail.setError(getContext().getResources().getString(R.string.Message_error_email_empty));
            return false;
        }

        else if(!email.matches(EmailRegex)){
            if(textEmail != null)
                textEmail.setError(getContext().getResources().getString(R.string.Message_error_email_invalid));
            return false;
        }
        else{
            if(textEmail != null)
                textEmail.setErrorEnabled(false);
            return true;
        }
    }

    public static boolean checkPassword(String password, TextInputLayout textPassword){

        if(password.matches(PasswordRegex)){
            if(textPassword != null)
                textPassword.setErrorEnabled(false);
            return true;
        }
        else{
            if(textPassword != null)
                messageErrorPassword(password, textPassword);
            return false;
        }
    }

    public static void messageErrorPassword(String password, TextInputLayout TextPassword){

        if(password.equals("")){
            TextPassword.setError(getContext().getResources().getString(R.string.Message_error_password_empty));
        }
        else if(!password.matches("^(?=.*[A-Z]).*$")) {
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

    public static boolean checkEULA(CheckBox checkBox){
        if(checkBox.isChecked())
            return true;
        else{
            checkBox.setError(getContext().getResources().getString(R.string.Message_error_eula));
            return false;
        }
    }

}
