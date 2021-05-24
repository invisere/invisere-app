package dam.invisere.gtidic.udl.cat.invisereapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import dam.invisere.gtidic.udl.cat.invisereapp.R;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Place;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Route;

public class RouteAdapter extends ListAdapter<Route, RouteAdapter.RouteHolder> {

    public RouteAdapter(@NonNull DiffUtil.ItemCallback<Route> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public RouteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route, null, false);

        return new RouteHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RouteHolder holder, int position) {
        Route currentRoute = getItem(position);
        Log.d("onBindViewHolder", "bindHolder() -> Route: " + currentRoute);

        Place[] listPlaces = currentRoute.getPoints();

        holder.routeName.setText("Name: " + currentRoute.getName());
        holder.routeDistance.setText("Distance: " +  currentRoute.getDistance());
        holder.routeDifficulty.setText("Difficulty: " + currentRoute.getDifficulty());
        Picasso.get().load(listPlaces[listPlaces.length-1].getPhoto()).error(R.drawable.ic_launcher_background).placeholder(R.drawable.progress_animation).resize(450, 450).into(holder.eventPoster);
        Picasso.get().load("https://static.vecteezy.com/system/resources/previews/001/907/701/non_2x/light-gray-paper-texture-free-photo.jpg").error(R.drawable.ic_launcher_background).placeholder(R.drawable.progress_animation).resize(2500,70).into(holder.bar);
    }

    class RouteHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "RouteCommonHolder";
        private TextView routeName;
        private TextView routeDifficulty;
        private TextView routeDistance;
        private ImageView eventPoster;
        private ImageView bar;
        private Button button;

        public RouteHolder(View itemView) {
            super(itemView);
            routeName = itemView.findViewById(R.id.eventNameInfo);
            routeDistance = itemView.findViewById(R.id.eventDescriptionInfo);
            eventPoster = itemView.findViewById(R.id.eventPosterInfo);
            bar = itemView.findViewById(R.id.blackBar);
            button = itemView.findViewById(R.id.button2);
            routeDifficulty = itemView.findViewById(R.id.routeDiff);
        }
    }
}
