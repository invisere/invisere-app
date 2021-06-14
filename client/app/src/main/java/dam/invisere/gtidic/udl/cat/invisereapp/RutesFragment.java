package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import dam.invisere.gtidic.udl.cat.invisereapp.adapter.RouteAdapter;
import dam.invisere.gtidic.udl.cat.invisereapp.adapter.RouteDiffCallback;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.ListRoutesViewModel;

public class RutesFragment extends Fragment {

    private static final String TAG = "RutesFragment";
    public RecyclerView recyclerView;
    public ListRoutesViewModel listRoutesViewModel;
    public RouteAdapter routeAdapter;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rutes, container, false);
        setHasOptionsMenu(true);

        listRoutesViewModel = new ListRoutesViewModel();
        routeAdapter = new RouteAdapter(new RouteDiffCallback());
        recyclerView = view.findViewById(R.id.activityMainRcyMain2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(routeAdapter);

        initView(view);
        return view;
    }

    public void initView(View view){
        listRoutesViewModel.getFavoriteRoutes();

        listRoutesViewModel.returnFavoriteRoutes().observe((LifecycleOwner) getContext(), routes -> {
            TextView rutes = view.findViewById(R.id.textViewRutesBuides);
            if(routes.size() == 0) {
                rutes.setText("Encara no has guardat cap ruta, \nquan ho facis les tindràs aquí!");
            } else {
                rutes.setVisibility(View.INVISIBLE);
            }
            routeAdapter.submitList(routes);
            //listRoutesViewModel.getRoutes();
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_rutes_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if(item.getItemId() == R.id.cardsRoutes) {
            Log.d(TAG, "onOptionsItemSelected: going to cards");
            Navigation.findNavController(view).navigate(R.id.nav_rutes_cards);
        }
        return super.onOptionsItemSelected(item);
    }
}