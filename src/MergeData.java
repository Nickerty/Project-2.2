import com.google.gson.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * The MergeData class is responsible for the merging phase of all the incoming data. Once data needs to get merged
 * this class will handle it. The 800 threads who handle the incoming data just put their data in this class.
 * The merging itself is handled by a single thread which will just copy the data, remove the old, and merge it.
 *
 * @author Matthijs van der Wal, Anne de Graaff
 * @version 1.0
 * @since 24-1-2020
 */

public class MergeData implements Runnable {

    int temp = 0;
    private volatile ConcurrentHashMap<Integer, Weatherstation> weatherstationById;
    private volatile ConcurrentHashMap<Integer, Weatherstation> tempData;
    private int amountOfData = 0;
    private int fileNumber = 0;
    /**
     * Constructor for the MergeData class
     */
    public MergeData() {
        weatherstationById = new ConcurrentHashMap<Integer, Weatherstation>();
        tempData = new ConcurrentHashMap<Integer, Weatherstation>();
    }

    /**
     * Method which will handle the merging process, this part is executed by a single thread
     */
    @Override
    public void run() {
        while (true) {
            while (!tempData.isEmpty()) {
//Checks if there is any data ready to get merged
//                adjustData("Clear", getData());                                                          //Puts all the data in temporary list to work with, makes the source list empty
                tempData.forEach((key, dataSingle) -> {                                                   //For every Weatherstation in the new data:
                    ArrayList<WeatherMeasurement> measurementList = new ArrayList<>(dataSingle.getWeatherMeasurements());       //Make list for all Measurements of the selected Weatherstation
//                    for (WeatherMeasurement measurementListItem : measurementList) {                           //Loop trough all the weather measurements:
                    WeatherMeasurement measurementListItem = measurementList.get(measurementList.size() - 1);
                    boolean isGefixt = false;                                                              //Variable to keep track if there is already a weatherstation saved which belongs to the currently receiving measurment
                    boolean stop = false;                                                                  //Variable to keep track if the currently receiving measurement is a duplicate.
                    int measurementId = measurementListItem.getStn();                                      //Get id of the station which belongs to the measurement
                    if (tempData.containsKey(measurementId)) {
                        Weatherstation weatherstation = tempData.get(measurementId);             //Get the already received measurements from a particular weatherstation
                        if (!weatherstation.getWeatherMeasurements().isEmpty()) {
                            ArrayList<WeatherMeasurement> weatherMeasurementArrayList = new ArrayList<>(weatherstation.getWeatherMeasurements());
                            for (WeatherMeasurement singleWeatherMeasurement : weatherMeasurementArrayList) {                        //Loop trough all the measurements of a particular weatherstation
                                if (singleWeatherMeasurement.getTime().equals(measurementListItem.getTime())) {                           //Check if the time of arrival is the same as the currently receiving measurement:
                                    stop = true;                                                                                    //Because this means this is a duplicate of a measurement it not need to be saved.
                                }
                            }
                        }
                        if (!stop) {                                                                    //Checks if the currently receiving measurement is not a duplicate.
//                            weatherstation.addWeatherMeasurement(measurementListItem);                 //Adds measurement to the correct station.
                            Weatherstation tempStation = new Weatherstation(measurementId);
                            tempStation.addWeatherMeasurement(measurementListItem);
                            tempData.replace(measurementId, tempStation);
//                                System.out.println(weatherstation.getWeatherMeasurements().size());
                        }
                        isGefixt = true;                                                               //Variable to keep track if there is already a weatherstation saved which belongs to the currently receiving measurment
                    }
                    if (!isGefixt) {                                                                         //Checks if all measurements where allocated to the corresponding weatherstation, if not:
                        Weatherstation newWeatherStation = new Weatherstation(measurementId);              //A new weatherstation would be made
                        newWeatherStation.addWeatherMeasurement(measurementListItem);                      //The corresponding measurement will linked to it
                        tempData.put(measurementId, newWeatherStation);               //Add data to a specific ID in the Hashmap
                        //System.out.println("First/Duplicate measure");
                    }
                });
                int counter = 0;
                for (Weatherstation weatherstation : tempData.values()) {
                    System.out.println(weatherstation);
                    for (WeatherMeasurement weatherMeasurement : weatherstation.getWeatherMeasurements()) {
                        counter += 1;
                    }
                }
                System.out.println(counter);
                writeToJsonFIle(this.tempData);
                this.tempData = new ConcurrentHashMap<>();
                System.out.println(this.tempData);
            }
        }
    }

    /**
     * Method for adjusting the hashmap: weatherstationById
     * @param data The data which the method needs to do something with
     */
    public synchronized void adjustData(HashMap<Integer, Weatherstation> data) {
            this.weatherstationById.putAll(data);               //Adds all received data to existing Hashmap
            amountOfData++;

            if(amountOfData >= 2) {
                this.tempData.putAll(this.weatherstationById);
                this.weatherstationById.clear();
                amountOfData = 0;
            }
    }


//    }

//    public void printIt(){
//            HashMap<Integer, Weatherstation> copyPasteWeatherStations = weatherstationById;
//            ArrayList<Weatherstation> copyPasteWeatherStationsList = new ArrayList<Weatherstation>(copyPasteWeatherStations.values());
//            String data = new Gson().toJson(copyPasteWeatherStationsList);
//            System.out.println("Current data: "+data);
//    }

    /**
     * Method for getting the HashMap tempData
     * @return Hashmap of the temporary data where the merger has to work with
     */
    public ConcurrentHashMap<Integer, Weatherstation> getTempData() {
        return this.tempData;
    }

    /**
     * Method for making a final JSON file which need to be send to the Virtual Machine
     */
    public synchronized void writeToJsonFIle(ConcurrentHashMap dataOut) {

        try {
            System.out.println("Writing to file: ding"+fileNumber+".json");
            PrintWriter writer = new PrintWriter("ding"+fileNumber+".json", "UTF-8");
            System.out.println("Size: " + dataOut.size());
            String data = new Gson().toJson(dataOut);
            writer.println(data);
            writer.close();
            fileNumber++;
        } catch(FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println(e);
        }

    }
}
