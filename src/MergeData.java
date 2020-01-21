import com.google.gson.*;

import java.util.*;

public class MergeData implements Runnable {

    int temp = 0;
    private ArrayList<Weatherstation> weatherstations = new ArrayList<Weatherstation>();

    public MergeData() {
    }

    @Override
    public void run() {
        while(true){
        }
    }

    public synchronized void addData(ArrayList<Weatherstation> data){

        if(weatherstations.size() == 0) {
            weatherstations.addAll(data);
        } else {
            for (Weatherstation dataSingle : data) {
                ArrayList<WeatherMeasurement> measurementList = dataSingle.getWeatherMeasurements();
                for (WeatherMeasurement measurementListItem : measurementList) {
                    boolean isGefixt = false;
                    boolean stop = false;
                    int meaurmentId = measurementListItem.getStn();
                    for (Weatherstation weatherstation : weatherstations) {
                        if (weatherstation.stn == meaurmentId) {
                            ArrayList<WeatherMeasurement> weatherMeasurementArrayList = weatherstation.getWeatherMeasurements();
                            for (WeatherMeasurement singleWeatherMeasurement :weatherMeasurementArrayList) {
                                if(singleWeatherMeasurement.getTime() == measurementListItem.getTime()) {
                                    stop = true;
                                }
                            }
                            if(!stop) {
                                weatherstation.addWeatherMeasurement(measurementListItem);
                            }
                            isGefixt = true;
                        }
                    }
                    if(isGefixt) {
                        System.out.println("Nice");
                    } else {
                        Weatherstation newWeatherStation = new Weatherstation(meaurmentId);
                        newWeatherStation.addWeatherMeasurement(measurementListItem);
                        weatherstations.add(newWeatherStation);
                    }
                }
            }
        }
        printIt();
    }

    public void printIt(){
            String data = new Gson().toJson(weatherstations);
            System.out.println(data);
    }



}
