package dam.invisere.gtidic.udl.cat.invisereapp.models;
import com.google.android.material.textfield.TextInputLayout;


public class LoginUtils {

    public static boolean checkPassword(String password, String confirmPassword, TextInputLayout textPassword, TextInputLayout textConfirmPassword){

        if(password.matches("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")){
            textPassword.setErrorEnabled(false);
            if(!password.equals(confirmPassword)){
                textConfirmPassword.setError("Las contraseñas no coinciden");
                return false;
            }
            else{
                textPassword.setErrorEnabled(false);
                textConfirmPassword.setErrorEnabled(false);
                return true;
            }

        }
        else{
            textConfirmPassword.setErrorEnabled(false);
            textPassword.setErrorEnabled(false);
            messageErrorPassword(password, textPassword);

            return false;
        }
    }

    public static void messageErrorPassword(String password, TextInputLayout TextPassword){

            if(!password.matches("(?:.*[A-Z]){1}")){
                TextPassword.setError("Password does not contain capital letters");
            }
            else if(!password.matches("(?=.*[0-9])")){
                TextPassword.setError("Password does not contain numbers");
            }
            else if(!password.matches("(?=.*[@#$%^&+=!])")){
                TextPassword.setError("Password does not contain special characters");
            }
            else {
                TextPassword.setError("Password has an invalid length");
            }
    }

    public static boolean checkName(String name, TextInputLayout textName){

        if(name.matches("^[a-z ,.'-]+$")){
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
            textName.setError("The name no puede estar vacio");
        }

        else if(name.matches("^[@#$%^&+=!]")){
            textName.setError("The name cannot contain special characters");
        }
        else {
            textName.setError("Name has an invalid length");
        }
    }

    public static boolean checkSurname(String surname, TextInputLayout textSurname){

        if(surname.matches("^[a-z ,.'-]+$")){
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
            textSurname.setError("The surname no puede estar vacio");
        }

        else if(surname.matches("^[@#$%^&+=!]")){
            textSurname.setError("The name cannot contain special characters");
        }
        else {
            textSurname.setError("Surname has an invalid length");
        }
    }

    public static boolean isValidEmailAddress(String email, TextInputLayout textEmail){
        String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

        if(email.equals("")){
            textEmail.setError("The email no puede estar vacio");
            return false;
        }

        else if(!email.matches(regex)){
            textEmail.setError("Enter a valid email");
            return false;
        }
        else{
            textEmail.setErrorEnabled(false);
            return true;
        }
    }

}
