package pt.ua.icm.hw2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherGroup {

    @Expose
    @SerializedName("owner")
    private String owner;

    @Expose
    @SerializedName("country")
    private String country;

    @Expose
    @SerializedName("globalIdLocal")
    private int globalIdLocal;


    @Expose
    @SerializedName("dataUpdate")
    private String dataUpdate;


    @Expose
    @SerializedName("data")
    private List<Weather> data;

    public WeatherGroup(String owner, String country, int globalIdLocal, String dataUpdate, List<Weather> data) {
        this.owner = owner;
        this.country = country;
        this.globalIdLocal = globalIdLocal;
        this.dataUpdate = dataUpdate;
        this.data = data;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getGlobalIdLocal() {
        return globalIdLocal;
    }

    public void setGlobalIdLocal(int globalIdLocal) {
        this.globalIdLocal = globalIdLocal;
    }

    public String getDataUpdate() {
        return dataUpdate;
    }

    public void setDataUpdate(String dataUpdate) {
        this.dataUpdate = dataUpdate;
    }

    public List<Weather> getData() {
        return data;
    }

    public void setData(List<Weather> data) {
        this.data = data;
    }
}
