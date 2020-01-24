import java.util.*;

/**
 * The WeatherMeasurement class is where we can store values for several weathervariables. Instances of this class
 * are linked to weatherstations.
 *
 * @author Matthijs van der Wal
 * @version 1.0
 * @since 24-1-2020
 */

public class WeatherMeasurement {
    private int stn;
    private String date;                    //YYYY-MM-DD
    private String time;                    //HH:MM:SS
    private double temperature;             //1 decimal
    private double dewpoint;                //1 decimal
    private double airPressureStationLevel; //1 decimal
    private double airPressureSeaLevel;     //1 decimal
    private double visibility;              //1 decimal
    private double windSpeed;               //1 decimal
    private double rainfall;                //2 decimals
    private double snowfall;                //1 decimal
    private String FRSHTT;                  //Freezing, Rain, Snow, Hail, Thunderstorm, Tornado
    private double cloudy;                  //1 decimal
    private double windDirection;            //0 decimals

    /**
     * Setter for the stationID field
     * @param stn stationID which belongs to this measurement
     */
    public void setStn(int stn) {
        this.stn = stn;
    }

    /**
     * Setter for the Date field
     * @param date Date which belongs to this measurement
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Setter for the time field
     * @param time Time which belongs to this measurement
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Setter for the temperature field
     * @param temperature Temperature value which belongs to this measurement
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    /**
     * Setter for the dewpoint field
     * @param dewpoint Dewpoint value which belongs to this measurement
     */
    public void setDewpoint(double dewpoint) {
        this.dewpoint = dewpoint;
    }

    /**
     * Setter for the air pressure at station level field
     * @param airPressureStationLevel Air pressure at station level value which belongs to this measurement
     */
    public void setAirPressureStationLevel(double airPressureStationLevel) {
        this.airPressureStationLevel = airPressureStationLevel;
    }

    /**
     * Setter for the air pressure at sea level field
     * @param airPressureSeaLevel Air pressure at sea level value which belongs to this measurement
     */
    public void setAirPressureSeaLevel(double airPressureSeaLevel) {
        this.airPressureSeaLevel = airPressureSeaLevel;
    }

    /**
     * Setter for the visibility field
     * @param visibility Visibility value which belongs to this measurement
     */
    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    /**
     * Setter for the wind speed field
     * @param windSpeed Wind speed value which belongs to this measurement
     */
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     * Setter for the rainfall field
     * @param rainfall Rainfall value which belongs to this measurement
     */
    public void setRainfall(double rainfall) {
        this.rainfall = rainfall;
    }

    /**
     * Setter for the snowfall field
     * @param snowfall snowfall value which belongs to this measurement
     */
    public void setSnowfall(double snowfall) {
        this.snowfall = snowfall;
    }

    /**
     * Setter for the FRSHTT field
     * @param FRSHTT Freezing, rain, snow, hail, thunderstorm, tornado values which belong to this measurement
     */
    public void setFRSHTT(String FRSHTT) {
        this.FRSHTT = FRSHTT;
    }

    /**
     * Setter for the cloudy field
     * @param cloudy Cloudy value which belongs to this measurement
     */
    public void setCloudy(double cloudy) {
        this.cloudy = cloudy;
    }

    /**
     * Setter for the wind direction field
     * @param windDirection Wind direction value which belongs to this measurement
     */
    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    /**
     * Getter for the stationID field
     * @return stationID which belongs to this measurement
     */
    public int getStn() {
        return stn;
    }

    /**
     * Getter for the date field
     * @return Date which belongs to this measurement
     */
    public String getDate() {
        return date;
    }

    /**
     * Getter for the time field
     * @return Time which belongs to this measurement
     */
    public String getTime() {
        return time;
    }

    /**
     * Getter for the temperature field
     * @return Temperature value which belongs to this measurement
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * Getter for the dewpoint field
     * @return Dewpoint value which belongs to this measurement
     */
    public double getDewpoint() {
        return dewpoint;
    }

    /**
     * Getter for the air pressure at station level field
     * @return Air pressure at station level value which belongs to this measurement
     */
    public double getAirPressureStationLevel() {
        return airPressureStationLevel;
    }

    /**
     * Getter for the air pressure at sea level field
     * @return Air pressure at sea level value which belongs to this measurement
     */
    public double getAirPressureSeaLevel() {
        return airPressureSeaLevel;
    }

    /**
     * Getter for the visibility field
     * @return visibility value which belongs to this measurement
     */
    public double getVisibility() {
        return visibility;
    }

    /**
     * Getter for the wind speed field
     * @return Wind speed value which belongs to this measurement
     */
    public double getWindSpeed() {
        return windSpeed;
    }

    /**
     * Getter for the rainfall field
     * @return Rainfall value which belongs to this measurement
     */
    public double getRainfall() {
        return rainfall;
    }

    /**
     * Getter for the snowfall field
     * @return Snowfall value which belongs to this measurement
     */
    public double getSnowfall() {
        return snowfall;
    }

    /**
     * Getter for the FRSHTT field
     * @return Freezing, rain, snow, hail, thunderstorm, tornado values which belong to this measurement
     */
    public String getFRSHTT() {
        return FRSHTT;
    }

    /**
     * Getter for the cloudy field
     * @return Cloudy value which belongs to this measurement
     */
    public double getCloudy() {
        return cloudy;
    }

    /**
     * Getter for the wind direction field
     * @return Wind direction value which belongs to this measurement
     */
    public double getWindDirection() {
        return windDirection;
    }
}
