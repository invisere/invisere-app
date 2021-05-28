package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import dam.invisere.gtidic.udl.cat.invisereapp.databinding.FragmentDetallsPuntsBinding;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Place;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.Utils;

public class DetallsPuntsFragment extends Fragment {

    private AccountRepo accountRepo;

    public DetallsPuntsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountRepo = new AccountRepo();
        accountRepo.get_routes(Utils.getToken());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentDetallsPuntsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detalls_punts, container, false);
        View view = binding.getRoot();

        accountRepo.getRoutesList().observe(getViewLifecycleOwner(), routes -> {
            Place[] places = accountRepo.getRoutesList().getValue().get(0).getPoints();
            binding.setPlace(places[0]);
        });


        return view;
    }
}