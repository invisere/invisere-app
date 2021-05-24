package dam.invisere.gtidic.udl.cat.invisereapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import dam.invisere.gtidic.udl.cat.invisereapp.adapter.RouteAdapter;
import dam.invisere.gtidic.udl.cat.invisereapp.adapter.RouteDiffCallback;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.ListRoutesViewModel;

public class RoutesListActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public ListRoutesViewModel listRoutesViewModel;
    public RouteAdapter routeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_list);

        routeAdapter = new RouteAdapter(new RouteDiffCallback());
        recyclerView = findViewById(R.id.activityMainRcyMain2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(routeAdapter);

        initView();
    }

    public void initView(){
        listRoutesViewModel = new ListRoutesViewModel();

        listRoutesViewModel.getRoutes();

        listRoutesViewModel.returnRoutes().observe(this, routes -> {
            routeAdapter.submitList(routes);
            //listRoutesViewModel.getRoutes();
        });
    }



}