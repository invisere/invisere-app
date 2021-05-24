package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dam.invisere.gtidic.udl.cat.invisereapp.adapter.RouteAdapter;
import dam.invisere.gtidic.udl.cat.invisereapp.adapter.RouteDiffCallback;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.ListRoutesViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoutesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoutesListFragment extends Fragment {

    public RecyclerView recyclerView;
    public ListRoutesViewModel listRoutesViewModel;
    public RouteAdapter routeAdapter;

    public RoutesListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RoutesListFragment newInstance() {
        RoutesListFragment fragment = new RoutesListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        routeAdapter = new RouteAdapter(new RouteDiffCallback());
        recyclerView = recyclerView.findViewById(R.id.activityMainRcyMain2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(routeAdapter);

        initView();
        return inflater.inflate(R.layout.fragment_routes_list, container, false);
    }

    public void initView(){
        listRoutesViewModel = new ListRoutesViewModel();

        listRoutesViewModel.getRoutes();

        listRoutesViewModel.returnRoutes().observe((LifecycleOwner) getContext(), routes -> {
            routeAdapter.submitList(routes);
            //listRoutesViewModel.getRoutes();
        });
    }
}