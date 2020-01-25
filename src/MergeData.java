import com.google.gson.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;

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
    private volatile HashMap<Integer, Weatherstation> weatherstationById;
    private volatile HashMap<Integer, Weatherstation> tempData;
    private int amountOfData = 0;
    private int fileNumber = 0;
    /**
     * Constructor for the MergeData class
     */
    public MergeData() {
        weatherstationById = new HashMap<Integer, Weatherstation>();
        tempData = new HashMap<Integer, Weatherstation>();
    }

    /**
     * Method which will handle the merging process, this part is executed by a single thread
     */
    @Override
    public void run() {
        while (true) {
                                                                 //Checks if there is any data ready to get merged
//                adjustData("Clear", getData());                                                          //Puts all the data in temporary list to work with, makes the source list empty
                for (Weatherstation dataSingle : weatherstationById.values()) {                                                   //For every Weatherstation in the new data:
                    ArrayList<WeatherMeasurement> measurementList = dataSingle.getWeatherMeasurements();       //Make list for all Measurements of the selected Weatherstation
                    for (WeatherMeasurement measurementListItem : measurementList) {                           //Loop trough all the weather measurements:
                        boolean isGefixt = false;                                                              //Variable to keep track if there is already a weatherstation saved which belongs to the currently receiving measurment
                        boolean stop = false;                                                                  //Variable to keep track if the currently receiving measurement is a duplicate.
                        int measurementId = measurementListItem.getStn();                                      //Get id of the station which belongs to the measurement
                        if (weatherstationById.containsKey(measurementId)) {
                            Weatherstation weatherstation = weatherstationById.get(measurementId);             //Get the already received measurements from a particular weatherstation
                            ArrayList<WeatherMeasurement> weatherMeasurementArrayList = weatherstation.getWeatherMeasurements();
                            for (WeatherMeasurement singleWeatherMeasurement : weatherMeasurementArrayList) {                        //Loop trough all the measurements of a particular weatherstation
                                if (singleWeatherMeasurement.getTime() == measurementListItem.getTime()) {                           //Check if the time of arrival is the same as the currently receiving measurement:
                                    stop = true;                                                                                    //Because this means this is a duplicate of a measurement it not need to be saved.
                                }
                            }
                            if (!stop) {                                                                    //Checks if the currently receiving measurement is not a duplicate.
                                weatherstation.addWeatherMeasurement(measurementListItem);                 //Adds measurement to the correct station.
                                weatherstationById.replace(measurementId, weatherstation);
                                System.out.println(weatherstation.getWeatherMeasurements().size());
                            }
                            isGefixt = true;                                                               //Variable to keep track if there is already a weatherstation saved which belongs to the currently receiving measurment
                        }
                        if (!isGefixt) {                                                                         //Checks if all measurements where allocated to the corresponding weatherstation, if not:
                            Weatherstation newWeatherStation = new Weatherstation(measurementId);              //A new weatherstation would be made
                            newWeatherStation.addWeatherMeasurement(measurementListItem);                      //The corresponding measurement will linked to it
                            weatherstationById.put(measurementId, newWeatherStation);               //Add data to a specific ID in the Hashmap
                            //System.out.println("First/Duplicate measure");
                        }
                    }
                }
            }
        }

    /**
     * Method for adjusting the hashmap: weatherstationById
     * @param value A string to tell the method what to do. Two options are: Add & Clear
     * @param data The data which the method needs to do something with
     */
    public synchronized void adjustData(String value, HashMap<Integer, Weatherstation> data) {
        if(value.equals("Add")){
            this.weatherstationById.putAll(data);               //Adds all received data to existing Hashmap
            amountOfData++;
            if(amountOfData >= 800)  {
                writeToJsonFIle();
                amountOfData = 0;
            }
        }
//        else if(value.equals("Clear")){
//            this.tempData.putAll(data);                         //Adds all known data to temporary Hashmap
//            this.weatherstationById.clear();                    //Clears the Hashmap with data which has to be merged
//        }
        else{
            System.out.println("Something went wrong, please contact the programmer");
        }

    }

    /**
     * Method for adding a weatherstation to a specific ID
     * @param measurementId ID for tell the method which weatherstation it has to deal with, same as STN of a weatherstation
     * @param newWeatherStation A Weatherstation which needs to be linked to the corresponding ID
     */


    /**
     * Method to replace the old data which was linked to an ID with new data
     * @param measurementId ID for tell the method which weatherstation it has to deal with, same as STN of a weatherstation
     * @param newWeatherStation A Weatherstation which needs to be linked to the corresponding ID
     */
//    public synchronized void mergeData(int measurementId, Weatherstation newWeatherStation){
//        //Replaces a existing weatherstation with a new one
//    }

//    public void printIt(){
//            HashMap<Integer, Weatherstation> copyPasteWeatherStations = weatherstationById;
//            ArrayList<Weatherstation> copyPasteWeatherStationsList = new ArrayList<Weatherstation>(copyPasteWeatherStations.values());
//            String data = new Gson().toJson(copyPasteWeatherStationsList);
//            System.out.println("Current data: "+data);
//    }

    /**
     * Method for getting the HashMap weatherstationById
     * @return Hashmap of the newly achieved data
     */

    /**
     * Method for getting the HashMap tempData
     * @return Hashmap of the temporary data where the merger has to work with
     */
    public HashMap<Integer, Weatherstation> getTempData() {
        return this.tempData;
    }

    /**
     * Method for making a final JSON file which need to be send to the Virtual Machine
     */
    public void writeToJsonFIle() {

        try {
            System.out.println("Wrting to file: ding"+fileNumber+".json");
            PrintWriter writer = new PrintWriter("ding"+fileNumber+".json", "UTF-8");
            String data = new Gson().toJson(weatherstationById);
            writer.println(data);
            writer.close();
//            weatherstationById = new HashMap<Integer, Weatherstation>();
            tempData = new HashMap<Integer, Weatherstation>();
            fileNumber++;
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
