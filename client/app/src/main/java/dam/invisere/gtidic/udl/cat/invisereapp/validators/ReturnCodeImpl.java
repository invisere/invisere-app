package dam.invisere.gtidic.udl.cat.invisereapp.validators;

public class ReturnCodeImpl implements ReturnCodeI {

    public final boolean registerAccountSuccess;
    public int registerAccountReturnCode;

    public ReturnCodeImpl(boolean registerAccountSuccess) {
        this.registerAccountSuccess = registerAccountSuccess;
    }

    public ReturnCodeImpl(boolean registerAccountSuccess, int registerAccountReturnCode) {
        this.registerAccountSuccess = registerAccountSuccess;
        this.registerAccountReturnCode = registerAccountReturnCode;
    }

    @Override
    public boolean isAccountRegisterSuccess() {
        return registerAccountSuccess;
    }

    @Override
    public int getAccountRegisterReturnCode() {
        return registerAccountReturnCode;
    }
}
