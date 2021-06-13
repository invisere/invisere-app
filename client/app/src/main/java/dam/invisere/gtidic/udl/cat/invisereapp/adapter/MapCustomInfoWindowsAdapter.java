package dam.invisere.gtidic.udl.cat.invisereapp.adapter;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import dam.invisere.gtidic.udl.cat.invisereapp.R;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Place;

public class MapCustomInfoWindowsAdapter implements GoogleMap.InfoWindowAdapter {

    private final View customView;
    private List<Place> places;
    private PlaceCommonHolder placeHolder;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location location;

    public void setLocation(Location location) {
        this.location = location;
    }

    public MapCustomInfoWindowsAdapter(List<Place> places, Context context) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        getLastLocation();
        this.places = places;
        customView = LayoutInflater.from(context).inflate(R.layout.info_window, null);
        placeHolder = new PlaceCommonHolder(customView);
    }

    private void getLastLocation() {
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    setLocation(location);
                }
            }
        });
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View getInfoWindow(@NonNull @NotNull Marker marker) {
        return null;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View getInfoContents(@NonNull @NotNull Marker marker) {
        int position = Integer.parseInt(marker.getSnippet());
        placeHolder.bindHolder(places.get(position), location);
        return customView;
    }
}
