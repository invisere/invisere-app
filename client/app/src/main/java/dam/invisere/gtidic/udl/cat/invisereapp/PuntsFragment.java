package dam.invisere.gtidic.udl.cat.invisereapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import dam.invisere.gtidic.udl.cat.invisereapp.adapter.MapCustomInfoWindowsAdapter;
import dam.invisere.gtidic.udl.cat.invisereapp.databinding.LayoutBottomSheetBinding;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Place;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.Utils;

public class PuntsFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "PuntsFragment";
    private static final int LOCATION_PERMISSION_PERMISSION_CODE = 1234;

    private GoogleMap mMap;
    private AccountRepo accountRepo;

    public PuntsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountRepo = new AccountRepo();
        checkPermitLocation();
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
                Place p = places.get(i);
                LatLng position = new LatLng(p.getLatitude(), p.getLongitude());
                this.mMap.addMarker(new MarkerOptions()
                        .position(position)
                        .title(p.getName())
                        .snippet(String.valueOf(i))
//                        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(p)))
                );
                mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
            }
            mMap.setInfoWindowAdapter(new MapCustomInfoWindowsAdapter(places, getContext()));
            mMap.setOnInfoWindowClickListener(marker -> {
                Place place = places.get(Integer.valueOf(marker.getSnippet()));
                View bottomSheetView = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_sheet, view.findViewById(R.id.bottomSheetContainer));
                LayoutBottomSheetBinding binding = LayoutBottomSheetBinding.inflate(inflater, (ViewGroup) bottomSheetView, false);
                binding.setPlace(place);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
                bottomSheetDialog.setContentView(binding.bottomSheetContainer);
                ImageView imageView = binding.imageViewPlaceImage;
                Picasso.get().load(place.getPhoto().replace("127.0.0.1", "192.168.101.88")).error(R.drawable.ic_launcher_background).into(imageView);
                bottomSheetDialog.show();
            });
        });

        return view;
    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        this.mMap = googleMap;
        this.mMap.setMyLocationEnabled(true);
        accountRepo.get_places(Utils.getToken());
    }

    private Bitmap getMarkerBitmapFromView(Place place) {
        View customMarkerView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.info_window, null);

        ImageView placeImage = customMarkerView.findViewById(R.id.imageViewMarkerImage);
        Picasso.get().load(place.getPhoto().replace("127.0.0.1", "192.168.101.88")).error(R.drawable.ic_launcher_background).into(placeImage);
        TextView placeName = customMarkerView.findViewById(R.id.textViewMarkerName);
        placeName.setText(place.getName());

        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();

        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    private void checkPermitLocation() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Location permission granted", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    Toast.makeText(getContext(), "Location permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "onRequestPermissionsResult: permission denied");
                    checkPermitLocation();
                }
                break;
        }
    }
}