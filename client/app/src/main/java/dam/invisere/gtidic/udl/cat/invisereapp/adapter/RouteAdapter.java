package dam.invisere.gtidic.udl.cat.invisereapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import dam.invisere.gtidic.udl.cat.invisereapp.R;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Route;

public class RouteAdapter extends ListAdapter<Route, RouteAdapter.RouteHolder> {

    private RouteCommonHolder routeCommonHolder;

    public RouteAdapter(@NonNull DiffUtil.ItemCallback<Route> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public RouteAdapter.RouteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route, null, false);

        return new RouteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteAdapter.RouteHolder holder, int position) {
        Route currentRoute = getItem(position);

        routeCommonHolder.bindHolder(currentRoute);
    }

    public class RouteHolder extends RecyclerView.ViewHolder{

        public RouteHolder(@NonNull View itemView) {
            super(itemView);
            routeCommonHolder = new RouteCommonHolder(itemView);
        }
    }
}
