package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Route;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.Utils;

public class ListRoutesViewModel extends ViewModel {

    private AccountRepo accountRepo;

    public ListRoutesViewModel(){
        this.accountRepo = new AccountRepo();
    }

    public void getRoutes(){
        accountRepo.get_routes(Utils.getToken());
    }

    public MutableLiveData<List<Route>> returnRoutes(){
        return this.accountRepo.getRoutesList();
    }

}
