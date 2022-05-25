package pt.ua.icm.hw2.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.ua.icm.hw2.R;
import pt.ua.icm.hw2.model.Region;
import pt.ua.icm.hw2.model.RegionGroup;
import pt.ua.icm.hw2.network.GetDataService;
import pt.ua.icm.hw2.network.RegionResultsObserver;
import pt.ua.icm.hw2.network.RetrofitClientInstance;
import pt.ua.icm.hw2.network.WeatherClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RegionsAdapter adapter;
    private RecyclerView recyclerView;

    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        generateRegionsList();

        if (findViewById(R.id.weather_detail_container) != null) {
            mTwoPane = true;
        }
    }

    private void generateDataList(List<Region> regions) {
        recyclerView = findViewById(R.id.weather_list);
        adapter = new RegionsAdapter(regions);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void generateRegionsList() {
        HashMap<String, Region> regions = new HashMap<>();
        GetDataService apiService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<RegionGroup> call = apiService.getRegions();
        call.enqueue(new Callback<RegionGroup>() {
            @Override
            public void onResponse(Call<RegionGroup> call, Response<RegionGroup> response) {
                int statusCode = response.code();
                RegionGroup regionGroup = response.body();
                for(Region region : regionGroup.getRegions()) {
                    regions.put(region.getLocal(), region);
                }
                ArrayList regionsName = new ArrayList(regions.values());
                Collections.sort(regionsName, Region.RegionNameComparator);
                generateDataList(regionsName);
            }

            @Override
            public void onFailure(Call<RegionGroup> call, Throwable t) {
                Log.e("main", "error calling remote api: " + t.getLocalizedMessage());
                //listener.onFailure(t);
                Toast.makeText(getApplicationContext(),"Error calling remote api",Toast.LENGTH_SHORT).show();
            }
        });
    }

    class RegionsAdapter extends
            RecyclerView.Adapter<RegionsAdapter.RegionsViewHolder> {

        private List<Region> regions;

        public RegionsAdapter(List<Region> regions) {
            this.regions = regions;
        }

        @NonNull
        @Override
        public RegionsAdapter.RegionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.region_list_content,
                    parent, false);
            return new RegionsAdapter.RegionsViewHolder(mItemView, this);

        }

        @Override
        public void onBindViewHolder(@NonNull RegionsAdapter.RegionsViewHolder holder, int position) {
            Region mCurrent = regions.get(position);
            holder.wordItemView.setText(mCurrent.getLocal());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        int selectedRegion = mCurrent.getGlobalLocal();
                        WeatherFragment fragment = WeatherFragment.newInstance(selectedRegion);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.weather_detail_container, fragment)
                                .addToBackStack(null)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, WeatherByRegionActivity.class);
                        intent.putExtra("regionId", mCurrent.getGlobalLocal());
                        context.startActivity(intent);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return regions.size();
        }

        class RegionsViewHolder extends RecyclerView.ViewHolder {

            final TextView wordItemView;
            final View mView;
            final RegionsAdapter mAdapter;

            public RegionsViewHolder(View itemView, RegionsAdapter adapter) {
                super(itemView);
                mView = itemView;
                wordItemView = (TextView) itemView.findViewById(R.id.region);
                this.mAdapter = adapter;
            }
        }

    }
}