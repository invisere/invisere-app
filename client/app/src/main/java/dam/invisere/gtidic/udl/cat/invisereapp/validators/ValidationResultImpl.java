package dam.invisere.gtidic.udl.cat.invisereapp.validators;

public class ValidationResultImpl implements ValidationResultI {

    private final String message;
    private final boolean success;

    public ValidationResultImpl(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public boolean isSuccess() {
        return this.success;
    }
}
