package dam.invisere.gtidic.udl.cat.invisereapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dam.invisere.gtidic.udl.cat.invisereapp.R;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Place;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<Place> places;

    public CardAdapter(List<Place> places) {
        this.places = places;
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
        holder.setData(places.get(position));
    }

    @Override
    public int getItemCount() {
        return places.size();
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

        void setData(Place data) {
            Picasso.get()
                    .load(data.getPhoto().replace("127.0.0.1", "192.168.101.88"))
                    .fit()
                    .centerCrop()
                    .into(image);
            nama.setText(data.getName());
            usia.setText(data.getAdress());
            kota.setText(data.getPhone());
        }
    }

    public List<Place> getItems() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}
