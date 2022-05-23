package pt.ua.icm.hw2.network;

import android.util.Log;

import java.util.HashMap;

import pt.ua.icm.hw2.model.Region;
import pt.ua.icm.hw2.model.RegionGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WeatherClient {

    private GetDataService apiService;

    public WeatherClient() {
        Retrofit retrofitInstance = RetrofitClientInstance.getRetrofitInstance();
        this.apiService = retrofitInstance.create(GetDataService.class);
    }

    public void getRegionsList(RegionResultsObserver listener) {
        Log.d("CLIENT", "GET REGION LIST");
        HashMap<String, Region> regions = new HashMap<>();

        Call<RegionGroup> call = apiService.getRegions();
        call.enqueue(new Callback<RegionGroup>() {
            @Override
            public void onResponse(Call<RegionGroup> call, Response<RegionGroup> response) {
                int statusCode = response.code();
                Log.d("CLIENT", String.valueOf(statusCode));
                RegionGroup regionGroup = response.body();
                for(Region region : regionGroup.getRegions()) {
                    regions.put(region.getLocal(), region);
                }
                listener.receiveRegionsList(regions);
            }

            @Override
            public void onFailure(Call<RegionGroup> call, Throwable t) {
                    Log.e("main", "error calling remote api: " + t.getLocalizedMessage());
                    listener.onFailure(t);
            }
        });
    }
}
