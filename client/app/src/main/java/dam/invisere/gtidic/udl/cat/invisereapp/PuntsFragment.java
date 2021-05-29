package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.Utils;

public class PuntsFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "PuntsFragment";
    private GoogleMap mMap;
    private AccountRepo accountRepo;

    public PuntsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountRepo = new AccountRepo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_punts, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        accountRepo.getPlacesList().observe(getViewLifecycleOwner(), places -> {
            for (int i = 0; i < places.size(); i++) {
                LatLng position = new LatLng(places.get(i).getLatitude(), places.get(i).getLongitude());
                this.mMap.addMarker(new MarkerOptions()
                        .position(position)
                        .title(places.get(i).getName())
                        .snippet(String.valueOf(i))
//                        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(place)))
                );
                mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
            }
            mMap.setOnInfoWindowClickListener(marker -> {
                PuntsFragmentDirections.ActionNavPuntsToNavDetallsPunts action = PuntsFragmentDirections.actionNavPuntsToNavDetallsPunts(places.get(Integer.valueOf(marker.getSnippet())));
                Navigation.findNavController(view).navigate(action);
            });
        });

        return view;
    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        this.mMap = googleMap;
        accountRepo.get_places(Utils.getToken());
    }
}