package pt.ua.icm.hw2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Region {

    @Expose
    @SerializedName("globalIdLocal")
    private int globalLocal;

    @Expose
    @SerializedName("local")
    private String local;

    @Expose
    @SerializedName("latitude")
    private double latitude;

    @Expose
    @SerializedName("longitude")
    private double longitude;

    public Region(int globalLocal, String local, double latitude, double longitude) {
        this.globalLocal = globalLocal;
        this.local = local;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getGlobalLocal() {
        return globalLocal;
    }

    public void setGlobalLocal(int globalLocal) {
        this.globalLocal = globalLocal;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public static Comparator<Region> RegionNameComparator = new Comparator<Region>() {

        public int compare(Region r1, Region r2) {
            String RegionName1 = r1.getLocal().toUpperCase();
            String RegionName2 = r2.getLocal().toUpperCase();

            //ascending order
            return RegionName1.compareTo(RegionName2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};
}
