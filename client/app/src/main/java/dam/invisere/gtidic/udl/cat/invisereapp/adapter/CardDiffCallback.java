package dam.invisere.gtidic.udl.cat.invisereapp.adapter;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Route;

public class CardDiffCallback extends DiffUtil.Callback {

    private List<Route> old, baru;

    public CardDiffCallback(List<Route> old, List<Route> baru) {
        this.old = old;
        this.baru = baru;
    }

    @Override
    public int getOldListSize() {
        return old.size();
    }

    @Override
    public int getNewListSize() {
        return baru.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition).getName().equals(baru.get(newItemPosition).getName());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition) == baru.get(newItemPosition);
    }
}

