package dam.invisere.gtidic.udl.cat.invisereapp.adapter;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Place;

public class CardDiffCallback extends DiffUtil.Callback {

    private List<Place> old, baru;

    public CardDiffCallback(List<Place> old, List<Place> baru) {
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
        return old.get(oldItemPosition).getPhoto().replace("127.0.0.1", "192.168.101.88").equals(baru.get(newItemPosition).getPhoto().replace("127.0.0.1", "192.168.101.88"));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition) == baru.get(newItemPosition);
    }
}

