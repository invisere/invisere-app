package dam.invisere.gtidic.udl.cat.invisereapp.validators;

public class ReturnCodeImpl implements ReturnCodeI {

    private final boolean success;
    private int returnCode;
    private int msgCode;

    public ReturnCodeImpl(boolean success) {
        this.success = success;
    }

    public ReturnCodeImpl(boolean success, int returnCode, int msgCode) {
        this.success = success;
        this.returnCode = returnCode;
        this.msgCode = msgCode;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public int getReturnCode() {
        return returnCode;
    }

    @Override
    public int getMsgCode() {
        return msgCode;
    }
}
