package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import dam.invisere.gtidic.udl.cat.invisereapp.adapter.CardAdapter;
import dam.invisere.gtidic.udl.cat.invisereapp.adapter.CardDiffCallback;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Route;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.Utils;

public class CardsFragment extends Fragment {

    private static final String TAG = "CardsFragment";
    private CardStackLayoutManager manager;
    private CardAdapter adapter;
    private AccountRepo accountRepo;
    private View view;

    public CardsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_cards, container, false);
        accountRepo = new AccountRepo();
        accountRepo.get_routes(Utils.getToken());
        accountRepo.getRoutesList().observe(getViewLifecycleOwner(), routes -> init(view));
        return view;
    }

    private void init(View view) {
        CardStackView cardStackView = view.findViewById(R.id.card_stack_view);
        adapter = new CardAdapter(addCards());
        manager = new CardStackLayoutManager(getContext(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right){
                    Toast.makeText(getContext(), "Like", Toast.LENGTH_SHORT).show();
                    accountRepo.add_route_favorite(Utils.getToken(), accountRepo.getRoutesList().getValue().get(manager.getTopPosition()).getId()-1);
                }
                if (direction == Direction.Left){
                    Toast.makeText(getContext(), "Dislike", Toast.LENGTH_SHORT).show();
                }

                // Paginating
                if (manager.getTopPosition() == adapter.getItemCount() - 5){
                    paginate();
                }

            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.place_name);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView tv = view.findViewById(R.id.place_name);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }
        });
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        cardStackView.setLayoutManager(manager);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
        cardStackView.setAdapter(adapter);
    }

    private void paginate() {
        List<Route> old = adapter.getItems();
        List<Route> baru = new ArrayList<>(addCards());
        CardDiffCallback callback = new CardDiffCallback(old, baru);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        adapter.setRoutes(baru);
        hasil.dispatchUpdatesTo(adapter);
    }

    private List<Route> addCards() {
        List<Route> routes = new ArrayList<>();
        for (Route r : accountRepo.getRoutesList().getValue()) {
            routes.add(new Route(r.getId(), r.getName(), r.getDistance(), r.getDifficulty(), r.getPoints()));
        }
        return routes;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_rutes_cards_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if(item.getItemId() == R.id.favoriteRoutes) {
            Log.d(TAG, "onOptionsItemSelected: going to list");
            Navigation.findNavController(view).navigate(R.id.nav_rutes_list);
        }
        return super.onOptionsItemSelected(item);
    }
}