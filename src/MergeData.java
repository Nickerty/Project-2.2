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

        if(weatherstations.size() == 0) {                                                                  //If there is no data the first data will be just added without checks
            weatherstations.addAll(data);
    } else {                                                                                               //Otherwise the following check will be executed
            for (Weatherstation dataSingle : data) {                                                       //For every Weatherstation in the new data:
                ArrayList<WeatherMeasurement> measurementList = dataSingle.getWeatherMeasurements();       //Make list for all Measurements of the selected Weatherstation
                for (WeatherMeasurement measurementListItem : measurementList) {                           //Loop trough all the weather measurements:
                    boolean isGefixt = false;                                                              //Variable to keep track if there is already a weatherstation saved which belongs to the currently receiving measurment
                    boolean stop = false;                                                                  //Variable to keep track if the currently receiving measurement is a duplicate.
                    int measurementId = measurementListItem.getStn();                                      //Get id of the station which belongs to the measurment
                    for (Weatherstation weatherstation : weatherstations) {                                //Loop trough all the weatherstations
                        if (weatherstation.stn == measurementId) {                                         //Check if weatherstation ID is the same as the Measurement ID
                            ArrayList<WeatherMeasurement> weatherMeasurementArrayList = weatherstation.getWeatherMeasurements();    //Get the already received measurements from a particular weatherstation
                            for (WeatherMeasurement singleWeatherMeasurement :weatherMeasurementArrayList) {                        //Loop trough all the measurements of a particular weatherstation
                                if(singleWeatherMeasurement.getTime() == measurementListItem.getTime()) {                           //Check if the time of arrival is the same as the currently receiving measurement:
                                    stop = true;                                                                                    //Because this means this is a duplicate of a measurement it not need to be saved.
                                }
                            }
                            if(!stop) {                                                                    //Checks if the currently receiving measurement is not a duplicate.
                                weatherstation.addWeatherMeasurement(measurementListItem);                 //Adds measurement to the correct station.
                            }
                            isGefixt = true;                                                               //Variable to keep track if there is already a weatherstation saved which belongs to the currently receiving measurment
                        }
                    }
                    if(isGefixt) {                                                                         //Checks if all measurements where allocated to the corresponding weatherstation
                        System.out.println("Nice");
                    } else {                                                                               //Otherwise:
                        Weatherstation newWeatherStation = new Weatherstation(measurementId);              //A new weatherstation would be made
                        newWeatherStation.addWeatherMeasurement(measurementListItem);                      //The corresponding measurement will linked to it
                        weatherstations.add(newWeatherStation);                                            //The new weatherstation will be added to the list of the rest.
                    }
                }
            }
        }
        printIt();          //Print for checking is everything is working.
    }

    public void printIt(){
            String data = new Gson().toJson(weatherstations);
            System.out.println(data);
    }



}
