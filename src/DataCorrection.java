import java.util.*;

/**
 * The datacorrection program takes the data from the weather stations and corrects missing data by extrapolating
 * from the previous 30 values (or the next 30 if the error occurs in the first 30 values). It also corrects
 * temperature values that deviate for more than 20% of the average value
 *
 * @author Nick Scholma
 * @version 1.0
 * @since 21-1-2020
 */
public class DataCorrection {

    private ArrayList<Integer> weatherData = new ArrayList<>(); //field containing the data that needs to be corrected
    private HashMap<Integer, Weatherstation> weatherstations;
    private ArrayList<Double> temperature = new ArrayList<>();
    private ArrayList<Double> dewpoint = new ArrayList<>();
    private ArrayList<Double> airPressureStationLevel = new ArrayList<>();
    private ArrayList<Double> airPressureSeaLevel = new ArrayList<>();
    private ArrayList<Double> visibility = new ArrayList<>();
    private ArrayList<Double> windSpeed = new ArrayList<>();
    private ArrayList<Double> rainfall = new ArrayList<>();
    private ArrayList<Double> snowfall = new ArrayList<>();
    private ArrayList<String> FRSHTT = new ArrayList<>();
    private ArrayList<Double> cloudy = new ArrayList<>();
    private ArrayList<Short> windDirection = new ArrayList<>();


    /**
     * Simple constructor for the class
     *
     * @param weatherData A list of Integers that you want the class to correct.
     */
    public DataCorrection(HashMap<Integer, Weatherstation> weatherData) {
        this.weatherstations = weatherData;
    }

    /**
     * This runs the correctTheData method on the weather data
     *
     * @param args -
     */
    public void main(String args[]) {
        testData();
        correctTheData();
        for (Integer printable : weatherData) {
            System.out.println(printable);
        } //TODO gotta ccheck if the main method can't return it's field to the caller.
        System.out.println(correctTemperature(makeTestTemps(), 200));
        System.out.println(correctTemperature(makeTestTemps(), -50));
    }

    /**
     * Getter for the weatherdata field.
     *
     * @return an arraylist containing integers that represent the weatherdata.
     */
    public ArrayList<Integer> getWeatherData() {
        return weatherData;
    }

    /**
     * The main method for this class; the correction method takes the weather data and extrapolates any missing values,
     * based on the average of the previous/next 30 values. This may be used on any set of weather data.
     */
    private void correctTheData() {
        int index = 0; //simple index to keep track of where we are
        for (Integer data : weatherData) { //loop that walks through all the data
            if (data == null) { //if a null value is found in the data:
                ArrayList<Integer> previousThirtyValues = new ArrayList<>(); //a new empty arraylist is made
                int whereWeAt; //and a new int is made
                if (index < 29) {
                    whereWeAt = index + 1; //if the null value is in the first 30 values, then we're going to start the whereWeAt on 1 above the nullpointer, meaning that we effectively count the next 30 values, rather than the previous
                } else {
                    whereWeAt = index - 31; //otherwise we're going back 31 spaces because that way we grab the previous 30
                }
                for (int i = 0; i < 30; i++) { //for 30 steps:
                    if (weatherData.get(whereWeAt) != null) { //if the data is not another null
                        previousThirtyValues.add(weatherData.get(whereWeAt)); //grab that data and put it in the new list
                    } else {
                        i--;  //In this way we skip an additional null value, but still end up with a list containing 30 Integers
                    }
                    whereWeAt++; //makes us go to to the item in the weatherdata array
                }
                int sum = 0;
                for (Integer number : previousThirtyValues) {
                    sum += number;
                }
                Integer avg = sum / previousThirtyValues.size(); //this sums all values in the previousThirtyValues list and then divides that sum by the number of items, creating an average
                previousThirtyValues.clear(); //clears the list for the next null value we encounter in weatherData
                weatherData.set(index, avg); //sets the value at the index (the null) as the average we just calculated instead
            }
            index++; //finally, we increase the index by 1 in order to go to the next value in weatherData.
        }
    }

    public void get30Values() {                                                                     //Collect 30 values to be able to do the corection method.
        for (Map.Entry<Integer, Weatherstation> weatherstation : weatherstations.entrySet()) {
            Integer weatherStationID = weatherstation.getKey();
            Weatherstation specificWeatherStation = weatherstation.getValue();
            for (WeatherMeasurement weatherMeasurement : specificWeatherStation.getWeatherMeasurements()) {
                //HIER EEN ALLE WAARDES MEEST RECENTE WAARDES VAN ALLE SPECIFIEKE VARIABELEN TOEVOEGEN (BIJVOORBEELD TEMPERATUUR)
                temperature.add(weatherMeasurement.getTemperature());
                dewpoint.add(weatherMeasurement.getDewpoint());
            }
        }
    }

    /**
     * Method which checks and corrects the given temperature (in °C) if that value diverges for an amount greater than
     * 20% of the average value of the given temperature range. This method should only be used on temperature values
     * @param temps the previous (or next, I don't care) 30 temperature values (as an ArrayList of Integers) to set the average value to.
     * @param tempToCheck The temperature that you want to check to see if it is a divergent deviant.
     * @return returns the checked temperature as an Integer, which has been corrected if it was found to be a deviant.
     */
    private static Integer correctTemperature(ArrayList<Integer> temps, Integer tempToCheck) {
        Integer sum = 0;
        for (int i = 0; i < 30; i++) {
            sum += temps.get(i);
        }
        int avg = sum / temps.size(); //simple calculation to get the average value of the 30 values given
        double maxTemp = (avg + (0.2 * avg)); //sets the max tolerable temperature
        double minTemp = avg - (0.2 * avg); //sets the minimal tolerable temperature
        if (tempToCheck > maxTemp) { //if the checked temperature is greater than the max tolerable temp:
            int maxTempRound = (int)Math.round(maxTemp); //then we round down the max value
            Integer maxTempInteger = new Integer(maxTempRound); // and turn it into an Integer, so it fits in the ArrayList
            tempToCheck = maxTempInteger; //and the temperature that was found to be a deviant, will be corrected to the new Integer value.
        }
        else if (tempToCheck < minTemp) { //if the checked temperature is lesser than the minimal tolerable temp:
            int minTempRound = (int)Math.round(minTemp);
            Integer minTempInteger = new Integer(minTempRound);
            tempToCheck = minTempInteger; //same steps as for maxtemp, but applied to mintemp
        }
        else { //if the temperature is not a deviant:
            ; //we do nothing
        }
        return tempToCheck; //the checked temperature is returned, possibly corrected
    }

    /**
     * Simple method that generates an array of Integers that contain some null values, that we use to test the correctTheData method with
     */
    private void testData() {
        for (Integer i = 1; i < 91; i++) {
            weatherData.add(i);
        } //makes a list of 90 values, ranging from 1 to 90
        weatherData.set(6,null);
        weatherData.set(9,null);
        weatherData.set(55, null);
        weatherData.set(57,null); //substitutes some values with null's
    }

    /**
     * Simple method that generates test values to test the temperature corrector with
     * @return returns an ArrayList containing Integers 1 trough 30, of which the average value will be 15.
     */
    private ArrayList<Integer> makeTestTemps() {
        ArrayList<Integer> testTemps = new ArrayList<>();
        for (Integer i = 1; i < 31; i++) {
            testTemps.add(i);
        }
        return testTemps;
    }
}
