import com.google.gson.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;

public class MergeData implements Runnable {

    int temp = 0;
    private volatile HashMap<Integer, Weatherstation> weatherstationById;
    private volatile HashMap<Integer, Weatherstation> tempData;

    public MergeData() {
        weatherstationById = new HashMap<Integer, Weatherstation>();
        tempData = new HashMap<Integer, Weatherstation>();
    }

    @Override
    public void run() {
        while (true) {
//            System.out.println(getData());  //FOR SOME REASON THE PROGRAM AT THIS POINT ALWAYS THINKS THAT THE VARIABLE: newData is empty.
            if (!getData().isEmpty()) {
                adjustData("Clear", getData());
                ArrayList<Weatherstation> tempData = new ArrayList<>(getTempData().values());

                for (Weatherstation dataSingle : tempData) {                                                       //For every Weatherstation in the new data:
                    ArrayList<WeatherMeasurement> measurementList = dataSingle.getWeatherMeasurements();       //Make list for all Measurements of the selected Weatherstation
                    for (WeatherMeasurement measurementListItem : measurementList) {                           //Loop trough all the weather measurements:
                        boolean isGefixt = false;                                                              //Variable to keep track if there is already a weatherstation saved which belongs to the currently receiving measurment
                        boolean stop = false;                                                                  //Variable to keep track if the currently receiving measurement is a duplicate.
                        int measurementId = measurementListItem.getStn();                                      //Get id of the station which belongs to the measurement
                        if (weatherstationById.containsKey(measurementId)) {
                            Weatherstation weatherstation = weatherstationById.get(measurementId);    //Get the already received measurements from a particular weatherstation
                            ArrayList<WeatherMeasurement> weatherMeasurementArrayList = weatherstation.getWeatherMeasurements();
                            for (WeatherMeasurement singleWeatherMeasurement : weatherMeasurementArrayList) {                        //Loop trough all the measurements of a particular weatherstation
                                if (singleWeatherMeasurement.getTime() == measurementListItem.getTime()) {                           //Check if the time of arrival is the same as the currently receiving measurement:
                                    stop = true;                                                                                    //Because this means this is a duplicate of a measurement it not need to be saved.
                                }
                            }
                            if (!stop) {                                                                    //Checks if the currently receiving measurement is not a duplicate.
                                weatherstation.addWeatherMeasurement(measurementListItem);                 //Adds measurement to the correct station.
                                mergeData(measurementId, weatherstation);
                            }
                            isGefixt = true;                                                               //Variable to keep track if there is already a weatherstation saved which belongs to the currently receiving measurment
                        }
                        if (!isGefixt) {                                                                         //Checks if all measurements where allocated to the corresponding weatherstation, if not:
                            Weatherstation newWeatherStation = new Weatherstation(measurementId);              //A new weatherstation would be made
                            newWeatherStation.addWeatherMeasurement(measurementListItem);                      //The corresponding measurement will linked to it
                            insertData(measurementId, newWeatherStation);
                            //System.out.println("First/Duplicate measure");
                        }
                    }
                }
                writeToJsonFIle();        //Print for checking is everything is working.
            }
        }
    }

    public synchronized void adjustData(String value, HashMap<Integer, Weatherstation> data) {
        if(value.equals("Add")){
            this.weatherstationById.putAll(data);
            //System.out.println(this.data.isEmpty());
        }
        else if(value.equals("Clear")){
            this.tempData.putAll(data);
            this.weatherstationById.clear();
        }
        else{
            System.out.println("Something went wrong, please contact the programmer");
        }

    }

    public synchronized void insertData(int measurementId, Weatherstation newWeatherStation){
        weatherstationById.put(measurementId, newWeatherStation);
    }

    public synchronized void mergeData(int measurementId, Weatherstation newWeatherStation){
        weatherstationById.replace(measurementId, newWeatherStation);
    }

    public void printIt(){
            HashMap<Integer, Weatherstation> copyPasteWeatherStations = weatherstationById;
            ArrayList<Weatherstation> copyPasteWeatherStationsList = new ArrayList<Weatherstation>(copyPasteWeatherStations.values());
            String data = new Gson().toJson(copyPasteWeatherStationsList);
            System.out.println("Current data: "+data);
    }

    public HashMap<Integer, Weatherstation> getData() {
        return this.weatherstationById;
    }

    public HashMap<Integer, Weatherstation> getTempData() {
        return this.tempData;
    }

    public void writeToJsonFIle() {

        try {
            PrintWriter writer = new PrintWriter("ding.json", "UTF-8");
            HashMap<Integer, Weatherstation> copyPasteWeatherStations = weatherstationById;
            ArrayList<Weatherstation> copyPasteWeatherStationsList = new ArrayList<Weatherstation>(copyPasteWeatherStations.values());
            String data = new Gson().toJson(copyPasteWeatherStationsList);
            System.out.println(data);
            writer.println(data);
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
