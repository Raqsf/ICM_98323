package pt.ua.icm.hw2.network;

import pt.ua.icm.hw2.model.RegionGroup;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("distrits-islands.json")
    Call<RegionGroup> getRegions();
}
