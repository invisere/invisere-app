package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dam.invisere.gtidic.udl.cat.invisereapp.adapter.RouteAdapter;
import dam.invisere.gtidic.udl.cat.invisereapp.adapter.RouteDiffCallback;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.ListRoutesViewModel;

public class RutesFragment extends Fragment {

    public RecyclerView recyclerView;
    public ListRoutesViewModel listRoutesViewModel;
    public RouteAdapter routeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rutes, container, false);

        listRoutesViewModel = new ListRoutesViewModel();
        routeAdapter = new RouteAdapter(new RouteDiffCallback());
        recyclerView = view.findViewById(R.id.activityMainRcyMain2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(routeAdapter);

        initView();
        return view;
    }

    public void initView(){
        listRoutesViewModel.getRoutes();

        listRoutesViewModel.returnRoutes().observe((LifecycleOwner) getContext(), routes -> {
            routeAdapter.submitList(routes);
            //listRoutesViewModel.getRoutes();
        });
    }
}