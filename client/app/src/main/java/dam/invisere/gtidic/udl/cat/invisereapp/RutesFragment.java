package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dam.invisere.gtidic.udl.cat.invisereapp.adapter.RouteAdapter;
import dam.invisere.gtidic.udl.cat.invisereapp.adapter.RouteDiffCallback;
import dam.invisere.gtidic.udl.cat.invisereapp.databinding.FragmentLoginBinding;
import dam.invisere.gtidic.udl.cat.invisereapp.databinding.FragmentSignupBinding;
import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.ListRoutesViewModel;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.LoginViewModel;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.SignUpViewModel;

import static android.content.ContentValues.TAG;

public class RutesFragment extends Fragment {

    public RecyclerView recyclerView;
    public ListRoutesViewModel listRoutesViewModel;
    public RouteAdapter routeAdapter;

    // TODO: Rename and change types and number of parameters
    public static RutesFragment newInstance() {
        RutesFragment fragment = new RutesFragment();
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