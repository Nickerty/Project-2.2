import java.util.*;

/**
 * The Weatherstation class is where we can store several weathermeasurement which belong to a specific instance of
 * this class.
 *
 * @author Matthijs van der Wal
 * @version 1.0
 * @since 24-1-2020
 */
public class Weatherstation {
    int stn;
    ArrayList<WeatherMeasurement> weatherMeasurements = new ArrayList<>();

    /**
     * Constructor for the Weatherstation class
     * @param stn Station Numer, a unique identifier. This is needed for later identification
     */
    public Weatherstation(int stn) {
        this.stn = stn;
    }

    /**
     * Getter for the stn field
     * @return The id of the weatherstation.
     */
    public int getStn() {
        return stn;
    }

    /**
     * Setter for the stn field
     * @param stn Unique identifier, needed for later identification of the weather station
     */
    public void setStn(int stn) {
        this.stn = stn;
    }

    /**
     * Method for adding instances of WeatherMeasurements to this station
     * @param item One weatherMeasurement, which will be added to the weatherMeasurements field
     */
    public void addWeatherMeasurement(WeatherMeasurement item) {
        weatherMeasurements.add(item);
    }

    /**
     * Getter for all known WeatherMeasurements
     * @return all known WeatherMeasurements in form of an ArrayList
     */
    public ArrayList<WeatherMeasurement> getWeatherMeasurements() {
        return weatherMeasurements;
    }

    /**
     * Method which removes the oldest (and this first) value from the weatherMeasurements field
     */
    public void removeOldestValue(){
        weatherMeasurements.remove(0);
    }
}
