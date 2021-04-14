package dam.invisere.gtidic.udl.cat.invisereapp.validators;

public class ValidationResultImpl implements ValidationResultI {

    private final boolean success;
    private final String message;

    public ValidationResultImpl(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    @Override
    public boolean isSuccess() {
        return this.success;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
    
}