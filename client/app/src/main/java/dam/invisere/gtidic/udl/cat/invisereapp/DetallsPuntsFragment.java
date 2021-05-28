package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentDetallsPuntsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detalls_punts, container, false);
        View view = binding.getRoot();
        accountRepo.get_places(Utils.getToken());

        ImageView imageView = view.findViewById(R.id.imageView2);
        accountRepo.getPlacesList().observe(getViewLifecycleOwner(), places -> {
            Picasso.get().load(places.get(0).getPhoto().replace("127.0.0.1", "10.0.2.2")).error(R.drawable.ic_launcher_background).resize(500, 500).into(imageView);
            binding.setPlace(places.get(0));
        });


        return view;
    }
}