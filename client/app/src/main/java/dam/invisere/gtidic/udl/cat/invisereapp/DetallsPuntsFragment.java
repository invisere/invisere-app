package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import dam.invisere.gtidic.udl.cat.invisereapp.databinding.FragmentDetallsPuntsBinding;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Place;

public class DetallsPuntsFragment extends Fragment {

    public DetallsPuntsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Place place = DetallsPuntsFragmentArgs.fromBundle(getArguments()).getPlace();
        FragmentDetallsPuntsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detalls_punts, container, false);
        View view = binding.getRoot();

        ImageView imageView = view.findViewById(R.id.imageView2);
        Picasso.get().load(place.getPhoto().replace("127.0.0.1", "192.168.101.88")).error(R.drawable.ic_launcher_background).resize(500, 500).into(imageView);
        binding.setPlace(place);
//        accountRepo.getPlacesList().observe(getViewLifecycleOwner(), places -> {
//            Picasso.get().load(places.get(0).getPhoto().replace("127.0.0.1", "192.168.1.157")).error(R.drawable.ic_launcher_background).resize(500, 500).into(imageView);
//            binding.setPlace(places.get(0));
//        });

//        accountRepo.get_places(Utils.getToken());
        return view;
    }
}