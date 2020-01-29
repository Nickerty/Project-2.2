
import java.util.List;
import java.util.ArrayList;

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
     * @param stn Unique identifier. This is needed for later identification
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
     * @param stn Unique identifier, needed for later identification of the weatherstation
     */
    public void setStn(int stn) {
        this.stn = stn;
    }

    /**
     * Method for adding weathermeasurements to this station
     * @param item One weathermeasurement
     */
    public void addWeatherMeasurement(WeatherMeasurement item) {
        weatherMeasurements.add(item);
    }

    /**
     * Getter for all known weathermeasurements
     * @return all known weathermeasurements in form of an ArrayList
     */
    public ArrayList<WeatherMeasurement> getWeatherMeasurements() {
        return weatherMeasurements;
    }

    /**
     * Getter for a specific number of measurements
     * @param aantal Used to tell the method how many values it must return
     * @return Depending on the "aantal" variable, the amount of values requested in form of an ArrayList
     */
    public ArrayList<WeatherMeasurement> getSpecificNumberOfWeatherMeasurements(int aantal) {
        List<WeatherMeasurement> thirtyValues = weatherMeasurements.subList(weatherMeasurements.size()-(1+aantal), weatherMeasurements.size()-1);
        ArrayList<WeatherMeasurement> returnthis = new ArrayList<>(thirtyValues);
        return returnthis;
    }
}
