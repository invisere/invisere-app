package dam.invisere.gtidic.udl.cat.invisereapp.adapter;

import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import dam.invisere.gtidic.udl.cat.invisereapp.R;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Place;

public class PlaceCommonHolder {

    private final static String TAG = "PlaceCommonHolder";
    private ImageView placeImage;
    private TextView placeName;
    private TextView placeDistance;

    public PlaceCommonHolder(@NonNull View viewItem) {
        placeImage = viewItem.findViewById(R.id.imageViewMarkerImage);
        placeName = viewItem.findViewById(R.id.textViewMarkerName);
        placeDistance = viewItem.findViewById(R.id.textViewMarkerDistance);
    }

    public void bindHolder(Place p, Location currentLocation) {
        Log.d(TAG, "bindHolder: Place: " + p);
        Picasso.get().load(p.getPhoto().replace("127.0.0.1", "192.168.101.88")).placeholder(R.drawable.progress_animation).error(R.drawable.ic_launcher_background).into(placeImage);
        this.placeName.setText(p.getName());

        if (currentLocation != null) {
            float[] distance = new float[1];
            Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(), p.getLatitude(), p.getLongitude(), distance);

            Log.d(TAG, "bindHolder: distance: " + distance[0]);
            this.placeDistance.setText(new DecimalFormat("#.##").format((distance[0] / 1000)) + " km");
        }
    }

}
