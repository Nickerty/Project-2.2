import java.util.ArrayList;

/**
 * The datacorrection program takes the data from the weather stations and corrects missing data by extrapolating
 * from the previous 30 values (or the next 30 if the error occurs in the first 30 values). It also corrects
 * temperature values that deviate for more than 25% of the average value
 *
 * @author Nick Scholma
 * @version 1.0
 * @since 21-1-2020
 */
public class DataCorrection {

    private static ArrayList<Integer> weatherData = new ArrayList<>(); //field containing the data that needs to be corrected

    /**
     * Simple constructor for the class
     * @param weatherData A list of Integers that you want the class to correct.
     */
    public DataCorrection(ArrayList<Integer> weatherData) {
        this.weatherData = weatherData;
    }

    /**
     * This runs the correctTheData method on the weather data
     * @param args -
     */
    public static void main (String args[]) {
        testData();
        correctTheData();
        for (Integer printable : weatherData) {
            System.out.println(printable);
        } //TODO gotta ccheck if the main method can't return it's field to the caller.
    }

    /**
     * The main method for this class; the correction method takes the weather data and extrapolates any missing values,
     * based on the average of the previous/next 30 values. This may be used on any set of weather data.
     */
    private static void correctTheData() {
        int index = 0; //simple index to keep track of where we are

        for (Integer data : weatherData) { //loop that walks through all the data
            if (data == null) { //if a null value is found in the data:
                ArrayList<Integer> previousThirtyValues = new ArrayList<>(); //a new empty arraylist is made
                int whereWeAt; //and a new int is made
                if (index < 29) {
                    whereWeAt = index + 1; //if the null value is in the first 30 values, then we're going to start the whereWeAt on 1 above the nullpointer, meaning that we effectively count the next 30 values, rather than the previous
                }
                else {
                    whereWeAt = index - 31; //otherwise we're going back 31 spaces because that way we grab the previous 30
                }
                for (int i = 0; i < 30; i++) { //for 30 steps:
                    if (weatherData.get(whereWeAt) != null) { //if the data is not another null
                        previousThirtyValues.add(weatherData.get(whereWeAt)); //grab that data and put it in the new list
                    }
                    else {
                        i--;  //In this way we skip an additional null value, but still end up with a list containing 30 Integers
                    }
                    whereWeAt++; //makes us go to to the item in the weatherdata array
                }
                Integer sum = 0;
                for (Integer number : previousThirtyValues) {
                    sum += number;
                }
                sum = sum / previousThirtyValues.size(); //this sums all values in the previousThirtyValues list and then divides that sum by the number of items, creating an average
                previousThirtyValues.clear(); //clears the list for the next null value we encounter in weatherData
                weatherData.set(index, sum); //sets the value at the index (the null) as the average we just calculated instead
            }
            index++; //finally, we increase the index by 1 in order to go to the next value in weatherData.
        }
    }

    /**
     * Simple method that generates an array of Integers that contain some null values, that we use to test the correctTheData method with
     */
    private static void testData() {
        for (Integer i = 1; i < 91; i++) {
            weatherData.add(i);
        } //makes a list of 90 values, ranging from 1 to 90
        weatherData.set(6,null);
        weatherData.set(9,null);
        weatherData.set(55, null);
        weatherData.set(57,null); //substitutes some values with null's
    }

    /**
     * getter for the weatherdata field.
     * @return an arraylist containing integers that represent the weatherdata.
     */
    public static ArrayList<Integer> getWeatherData() {
        return weatherData;
    } //TODO add shit for temperature correction
}
