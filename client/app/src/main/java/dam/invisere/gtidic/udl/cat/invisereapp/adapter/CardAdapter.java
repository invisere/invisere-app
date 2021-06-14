package dam.invisere.gtidic.udl.cat.invisereapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import dam.invisere.gtidic.udl.cat.invisereapp.R;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Route;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<Route> routes;

    public CardAdapter(List<Route> routes) {
        this.routes = routes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.place_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(routes.get(position));
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView nama, usia, kota;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.place_image);
            nama = itemView.findViewById(R.id.place_name);
            usia = itemView.findViewById(R.id.place_address);
            kota = itemView.findViewById(R.id.place_phone);
        }

        void setData(Route data) {
            Picasso.get()
                    .load("http://127.0.0.1:8001/static/media/routes/route.png".replace("127.0.0.1", "192.168.101.88"))
                    .fit()
                    .centerCrop()
                    .into(image);
            nama.setText(data.getName());
            usia.setText(new DecimalFormat("#.##").format(data.getDistance()) + " km");
            kota.setText(data.getDifficulty());
        }
    }

    public List<Route> getItems() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}
