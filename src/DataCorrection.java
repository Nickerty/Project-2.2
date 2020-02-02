import java.util.*;

/**
 * The datacorrection program takes the data from the weather stations and corrects missing data by extrapolating
 * from a given list of 30 values. It also contains a method for correcting temperature values that deviate for more
 * than 20% of the average value.
 *
 * @author Nick Scholma
 * @version 1.2
 * @since 1-2-2020
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
     * @param weatherData an ArrayList of Doubles that will be used to extrapolated a value
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
     * @param temps       An ArrayList of preferably 30 Double values representing temperature, which will be used to calculate the acceptable range of temperatures.
     * @param tempToCheck The Double value representing a temperature that you want to check to see if it is a divergent deviant.
     * @return returns the checked temperature as an Double, which has been corrected if it was found to be a deviant.
     */
    public Double correctTemperature(ArrayList<Double> temps, Double tempToCheck) {
        Double sum = 0.0;
        for (Double temp : temps) {
            sum += temp;
        } //a sum is made of all temperature values in the given ArrayList
        Double avg = sum / temps.size(); //simple calculation to get the average value of the values given
        Double maxTemp = (avg + (0.2 * avg)); //sets the max tolerable temperature
        Double minTemp = avg - (0.2 * avg); //sets the minimal tolerable temperature
        if (avg < 0) { //if the avg value is below 0 (which is very realistic as we are working with Celsius)
            Double temTemp = maxTemp;
            maxTemp = minTemp;
            minTemp = temTemp; //then the max and min tolerable values are swapped, seeing as e.g. -5 is smaller than -2
        }
        if (tempToCheck > maxTemp) { //if the checked temperature is greater than the max tolerable temp:
            tempToCheck = maxTemp; //then the temperature is a deviant, and will be corrected to the maximum tolerable value.
        } else if (tempToCheck < minTemp) { //same steps as for maxTemp, but applied to minTemp
            tempToCheck = minTemp; //the deviant value is set to the minimal tolerable value
        }
        return Math.round(tempToCheck * 10) / 10.0; //the checked temperature is returned, possibly corrected
    }
}
