package dam.invisere.gtidic.udl.cat.invisereapp.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

import java.util.List;

import dam.invisere.gtidic.udl.cat.invisereapp.R;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Place;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Route;

public class RouteCommonHolder {

    private static final String TAG = "RouteCommonHolder";
    private TextView routeName;
    private TextView routeDistance;
    private ImageView eventPoster;
    private ImageView eventType;
    private TextView eventStatus;
    private TextView eventStatusColor;

    public RouteCommonHolder(@NonNull View itemView) {
        routeName = itemView.findViewById(R.id.eventNameInfo);
        routeDistance = itemView.findViewById(R.id.eventDescriptionInfo);
        eventPoster = itemView.findViewById(R.id.eventPosterInfo);
        eventStatus = itemView.findViewById(R.id.eventStatusInfo);
        eventStatusColor = itemView.findViewById(R.id.eventStatusColourInfo);
        eventType = itemView.findViewById(R.id.eventTypeIconInfo);
    }

    public void bindHolder(Route r) {
        Log.d(TAG, "bindHolder() -> Route: " + r);

        Place[] listPlaces = r.getPoints();

        this.routeName.setText(r.getName());
        this.routeDistance.setText("" +  r.getDistance());
        //this.eventStatus.setText(e.getStatus().name());
        Picasso.get().load(listPlaces[listPlaces.length-1].getPhoto()).error(R.drawable.ic_launcher_background).placeholder(R.drawable.progress_animation).resize(350, 350).into(eventPoster);

        //this.eventStatusColor.setBackground(ContextCompat.getDrawable(this.eventStatusColor.getContext(), EventStatus.getColourResource(e.getStatus())));
        //this.eventStatus.setText(r.getStatus().getName());
        //this.eventStatus.setTextColor(ContextCompat.getColor(this.eventStatus.getContext(), EventStatus.getColourResource(e.getStatus())));

        //Log.d(TAG, "onBindViewHolder() -> cEvent: " + r.getPoster_url());
        //Picasso.get().load(e.getPoster_url()).into(this.eventPoster);

        //this.eventType.setImageResource(EventType.getImageResource(e.getType()));
    }


}
