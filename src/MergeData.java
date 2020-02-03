import com.google.gson.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * The MergeData class is responsible for the merging phase of all the incoming data. Once data needs to get merged
 * this class will handle it. The 800 threads which handle the incoming data put their data in this class.
 * The merging itself is handled by a single thread which will copy the data, remove the old, and then merge it.
 *
 * @author Matthijs van der Wal, Anne de Graaff
 * @version 1.1
 * @since 24-1-2020
 */

public class MergeData implements Runnable {

    private volatile ConcurrentHashMap<Integer, Weatherstation> weatherstationById;
    private volatile ConcurrentHashMap<Integer, Weatherstation> tempData;
    private int amountOfData = 0;
    private int fileNumber = 0;
    private int delayCounter = 0;
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
                ConcurrentHashMap<Integer, Weatherstation> dataOut = new ConcurrentHashMap<>();
                for (Weatherstation dataSingle : tempData.values()) {                                                   //For every Weatherstation in the new data:
                    ArrayList<WeatherMeasurement> measurementList = new ArrayList<>(dataSingle.getWeatherMeasurements());       //Make list for all Measurements of the selected Weatherstation

                    if (measurementList.size() >= 1) {
                        int maxTillTen = 10;
                        if (measurementList.size() < 10) {
                            maxTillTen = measurementList.size();
                        }
                        WeatherMeasurement measurementItem = measurementList.get(0);
                        int measurementId = measurementItem.getStn();
                        Weatherstation tempStation = new Weatherstation(measurementId);                                    //Get id of the station which belongs to the measurement
                        for (int count = 0; count < maxTillTen; count++) {
                            WeatherMeasurement measurementListItem = measurementList.get(measurementList.size() - (1 + count));
                            boolean isGefixt = false;                                                              //Variable to keep track if there is already a weatherstation saved which belongs to the currently receiving measurment
                            boolean stop = false;                                                                  //Variable to keep track if the currently receiving measurement is a duplicate.
                            if (dataOut.containsKey(measurementId)) {
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
                                    tempStation.addWeatherMeasurement(measurementListItem);
                                }
                                isGefixt = true;                                                               //Variable to keep track if there is already a weatherstation saved which belongs to the currently receiving measurment
                            }
                            if (!isGefixt) {                                                                         //Checks if all measurements where allocated to the corresponding weatherstation, if not:
                                tempStation.addWeatherMeasurement(measurementListItem);                      //The corresponding measurement will be linked to it
                            }
                        }
                        dataOut.put(measurementId, tempStation);                                                //Add data to a specific ID in the HashMap
                    }
                }

                if (delayCounter >= 10) {
                    writeToJsonFIle(dataOut);
                    this.tempData.clear();
                    dataOut.clear();
                    delayCounter = 0;
                }
            }
        }
    }

    /**
     * Method for adjusting the HashMap: weatherstation ById
     * @param data The data, consisting of a HashMap of weatherstations keyed by their STN, which this method uses
     */
    public synchronized void adjustData(HashMap<Integer, Weatherstation> data) {
        this.weatherstationById.putAll(data);               //Adds all received data to existing HashMap
        amountOfData++;

        if(amountOfData >= 800) {
            this.tempData.putAll(this.weatherstationById);
            this.weatherstationById.clear();
            amountOfData = 0;
            delayCounter+=1;
        }
    }

    /**
     * Method for making a final JSON file which need to be send to the Virtual Machine
     * @param dataOut Variable for the data which will be exported as a JSON file.
     */
    public synchronized void writeToJsonFIle(ConcurrentHashMap dataOut) {

        try {
            PrintWriter writer = new PrintWriter("File" + 100000 + fileNumber + ".json", "UTF-8");
            String data = new Gson().toJson(dataOut);
            writer.println(data);
            writer.close();
            fileNumber++;
            this.delayCounter = 0;
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println(e);
        }
    }
}