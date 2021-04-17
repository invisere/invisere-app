package dam.invisere.gtidic.udl.cat.invisereapp.validators;

public class ValidationResultImpl implements ValidationResultI {

    private final boolean success;
    private final int message;

    public ValidationResultImpl(boolean success, int message) {
        this.success = success;
        this.message = message;
    }

    @Override
    public boolean isSuccess() {
        return this.success;
    }

    @Override
    public int getMessage() {
        return this.message;
    }
    
}