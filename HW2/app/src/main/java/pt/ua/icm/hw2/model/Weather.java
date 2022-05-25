package pt.ua.icm.hw2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {

    @Expose
    private int globalIdLocal;

    @Expose
    @SerializedName("precipitaProb")
    private double precipitationProb;

    @Expose
    @SerializedName("tMin")
    private double tMin;

    @Expose
    @SerializedName("tMax")
    private double tMax;

    @Expose
    @SerializedName("predWindDir")
    private String predWindDir;

    @Expose
    @SerializedName("idWeatherType")
    private int idWeatherType;

    @Expose
    @SerializedName("classWindSpeed")
    private int classWindSpeed;

    @Expose
    @SerializedName("forecastDate")
    private String forecastDate;

    public Weather(double precipitationProb, double tMin, double tMax, String predWindDir, int idWeatherType, int classWindSpeed, String forecastDate) {
        this.precipitationProb = precipitationProb;
        this.tMin = tMin;
        this.tMax = tMax;
        this.predWindDir = predWindDir;
        this.idWeatherType = idWeatherType;
        this.classWindSpeed = classWindSpeed;
        this.forecastDate = forecastDate;
        this.globalIdLocal = 0;
    }

    public int getGlobalIdLocal() {
        return globalIdLocal;
    }

    public void setGlobalIdLocal(int globalIdLocal) {
        this.globalIdLocal = globalIdLocal;
    }

    public double getPrecipitationProb() {
        return precipitationProb;
    }

    public void setPrecipitationProb(double precipitationProb) {
        this.precipitationProb = precipitationProb;
    }

    public double gettMin() {
        return tMin;
    }

    public void settMin(double tMin) {
        this.tMin = tMin;
    }

    public double gettMax() {
        return tMax;
    }

    public void settMax(double tMax) {
        this.tMax = tMax;
    }

    public String getPredWindDir() {
        return predWindDir;
    }

    public void setPredWindDir(String predWindDir) {
        this.predWindDir = predWindDir;
    }

    public int getIdWeatherType() {
        return idWeatherType;
    }

    public void setIdWeatherType(int idWeatherType) {
        this.idWeatherType = idWeatherType;
    }

    public int getClassWindSpeed() {
        return classWindSpeed;
    }

    public void setClassWindSpeed(int classWindSpeed) {
        this.classWindSpeed = classWindSpeed;
    }

    public String getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(String forecastDate) {
        this.forecastDate = forecastDate;
    }

    @Override
    public String toString() {
        return forecastDate + '\n' +
                " Precipitation Probability: " + precipitationProb + "%\n" +
                " Min: " + tMin + "ºC\n" +
                " Max: " + tMax + "ºC\n" +
                " Predominant Wind Direction: " + predWindDir + '\n';
    }
}
