

import java.util.ArrayList;

public class Weatherstation {
    int stn;
    ArrayList<WeatherMeasurement> weatherMeasurements = new ArrayList<>();

    public Weatherstation(int stn) {
        this.stn = stn;
    }

    public int getStn() {
        return stn;
    }

    public void setStn(int stn) {
        this.stn = stn;
    }

    public void addWeatherMeasurement(WeatherMeasurement item) {
        weatherMeasurements.add(item);
    }

    public ArrayList<WeatherMeasurement> getWeatherMeasurements() {
        return weatherMeasurements;
    }

    public ArrayList<WeatherMeasurement> getSpecificNumberOfWeatherMeasurements(int aantal) { return (ArrayList<WeatherMeasurement>) weatherMeasurements.subList(weatherMeasurements.size()-(1+aantal), weatherMeasurements.size()-1);}
}
