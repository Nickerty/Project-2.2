import java.util.*;

/**
 * The datacorrection program takes the data from the weather stations and corrects missing data by extrapolating
 * from a given list of 30 values. It also contains a method for correcting temperature values that deviate for more
 * than 20% of the average value.
 *
 * @author Nick Scholma
 * @version 1.1
 * @since 23-1-2020
 */
public class DataCorrection {


    /**
     * Simple constructor for the class
     */
    public DataCorrection() {

    }

    /**
     * The main method for this class; the correction method takes the weather data and extrapolates any missing values,
     * based on the average of the previous/next 30 values. This may be used on any set of weather data.
     *
     * @param weatherData an arraylist of Doubles that will be used to extrapolated a value
     * @return returns an extrapolated value based on the given ArrayList
     */
    public Double correctTheData(ArrayList<Double> weatherData) {
        if (weatherData.isEmpty()) {
            return 0.00; //if the given list is empty, it means the first value given is a null, in which case we can only fill in a 0.00 with nothing else to extrapolate from.
        } else {
            Double sum = 0.00;
            for (Double data : weatherData) {
                sum += data; // simple loop that sums all the data and stores it in the sum variable
            }
            return sum / weatherData.size(); //the sum is divided by the number of items given, which gives us an extrapolated average, which is then returned
        }
    }

    /**
     * Method which checks and corrects the given temperature (in °C) if that value diverges for an amount greater than
     * 20% of the average value of the given temperature range. This method should only be used on temperature values!
     *
     * @param temps       An ArrayList of preferably 30 Double values reperesenting temperature, which will be used to calculate the acceptable range of temperatures.
     * @param tempToCheck The Double value representing a temperature that you want to check to see if it is a divergent deviant.
     * @return returns the checked temperature as an Double, which has been corrected if it was found to be a deviant.
     */
    public Double correctTemperature(ArrayList<Double> temps, Double tempToCheck) {
        Double sum = 0.0;
        for (int i = 0; i < 30; i++) {
            sum += temps.get(i);
        } //a sum is made of the first 30 values in the gven range, which is why you should strive to give this method lists that contain exactly 30 values
        Double avg = sum / temps.size(); //simple calculation to get the average value of the 30 values given
        Double maxTemp = (avg + (0.2 * avg)); //sets the max tolerable temperature
        Double minTemp = avg - (0.2 * avg); //sets the minimal tolerable temperature
        if (tempToCheck > maxTemp) { //if the checked temperature is greater than the max tolerable temp:
            tempToCheck = maxTemp; //then the temperature is a deviant, and will be corrected to the maximum tolerable value.
        } else if (tempToCheck < minTemp) { //same steps as for maxTemp, but applied to minTemp
            tempToCheck = minTemp; //the deviant value is set to the minimal tolerable value
        }
        return tempToCheck; //the checked temperature is returned, possibly corrected
    }


    /**
     * Simple method that generates an array of Integers that contain some null values, that we use to test the correctTheData method with
     *
     * @return returns the list
     */
//    private ArrayList<Double> testData() {
//        for (Double i = 1.00; i < 91.00; i++) {
//            weatherData.add(i);
//        } //makes a list of 90 values, ranging from 1 to 90
//        weatherData.set(6, null);
//        weatherData.set(9, null);
//        weatherData.set(55, null);
//        weatherData.set(57, null); //substitutes some values with null's
//    }

    /**
     * Simple method that generates test values to test the temperature corrector with
     *
     * @return returns an ArrayList containing Doubles 1.00 trough 30.00, of which the average value will be 15.00.
     */
//    private ArrayList<Double> makeTestTemps() {
//        ArrayList<Double> testTemps = new ArrayList<>();
//        for (Double i = 1.00; i < 31.00; i++) {
//            testTemps.add(i);
//        }
//        return testTemps;
//    }
}
