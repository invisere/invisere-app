package dam.invisere.gtidic.udl.cat.invisereapp.validators;

public class ReturnCodeImpl implements ReturnCodeI {

    public final boolean success;
    public int returnCode;

    public ReturnCodeImpl(boolean success) {
        this.success = success;
    }

    public ReturnCodeImpl(boolean success, int returnCode) {
        this.success = success;
        this.returnCode = returnCode;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public int getReturnCode() {
        return returnCode;
    }
}
