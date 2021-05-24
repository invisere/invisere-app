package dam.invisere.gtidic.udl.cat.invisereapp.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Route;

public class RouteDiffCallback extends DiffUtil.ItemCallback<Route> {

        @Override
        public boolean areItemsTheSame(@NonNull Route oldItem, @NonNull Route newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Route oldItem, @NonNull Route newItem) {
            return oldItem.equals(newItem);
        }

}

