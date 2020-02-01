import java.util.*;

/**
 * The WeatherMeasurement class is where we can store values for several weather variables. Instances of this class
 * are linked to Weatherstations.
 *
 * @author Matthijs van der Wal
 * @version 1.0
 * @since 24-1-2020
 */

public class WeatherMeasurement {
    private int stn;
    private String date;                    //YYYY-MM-DD
    private String time;                    //HH:MM:SS
    private double temp;             //1 decimal
    private double dew;                //1 decimal
    private double aPSL; //1 decimal
    private double aPSeL;     //1 decimal
    private double vis;              //1 decimal
    private double wiSp;               //1 decimal
    private double rf;                //2 decimals
    private double sf;                //1 decimal
    private String FRSHTT;                  //Freezing, Rain, Snow, Hail, Thunderstorm, Tornado
    private double c;                  //1 decimal
    private double wD;            //0 decimals

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
     * @param temp Temperature value which belongs to this measurement
     */
    public void setTemperature(double temp) {
        this.temp = temp;
    }

    /**
     * Setter for the dewpoint field
     * @param dew Dewpoint value which belongs to this measurement
     */
    public void setDewpoint(double dew) {
        this.dew = dew;
    }

    /**
     * Setter for the air pressure at station level field
     * @param aPSL Air pressure at station level value which belongs to this measurement
     */
    public void setAirPressureStationLevel(double aPSL) {
        this.aPSL = aPSL;
    }

    /**
     * Setter for the air pressure at sea level field
     * @param aPSeL Air pressure at sea level value which belongs to this measurement
     */
    public void setAirPressureSeaLevel(double aPSeL) {
        this.aPSeL = aPSeL;
    }

    /**
     * Setter for the visibility field
     * @param vis Visibility value which belongs to this measurement
     */
    public void setVisibility(double vis) {
        this.vis = vis;
    }

    /**
     * Setter for the wind speed field
     * @param wiSp Wind speed value which belongs to this measurement
     */
    public void setWindSpeed(double wiSp) {
        this.wiSp = wiSp;
    }

    /**
     * Setter for the rainfall field
     * @param rf Rainfall value which belongs to this measurement
     */
    public void setRainfall(double rf) {
        this.rf = rf;
    }

    /**
     * Setter for the snowfall field
     * @param sf snowfall value which belongs to this measurement
     */
    public void setSnowfall(double sf) {
        this.sf = sf;
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
     * @param c Cloudy value which belongs to this measurement
     */
    public void setCloudy(double c) {
        this.c = c;
    }

    /**
     * Setter for the wind direction field
     * @param wD Wind direction value which belongs to this measurement
     */
    public void setWindDirection(double wD) {
        this.wD = wD;
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
        return temp;
    }

    /**
     * Getter for the dewpoint field
     * @return Dewpoint value which belongs to this measurement
     */
    public double getDewpoint() {
        return dew;
    }

    /**
     * Getter for the air pressure at station level field
     * @return Air pressure at station level value which belongs to this measurement
     */
    public double getAirPressureStationLevel() {
        return aPSL;
    }

    /**
     * Getter for the air pressure at sea level field
     * @return Air pressure at sea level value which belongs to this measurement
     */
    public double getAirPressureSeaLevel() {
        return aPSeL;
    }

    /**
     * Getter for the visibility field
     * @return visibility value which belongs to this measurement
     */
    public double getVisibility() {
        return vis;
    }

    /**
     * Getter for the wind speed field
     * @return Wind speed value which belongs to this measurement
     */
    public double getWindSpeed() {
        return wiSp;
    }

    /**
     * Getter for the rainfall field
     * @return Rainfall value which belongs to this measurement
     */
    public double getRainfall() {
        return rf;
    }

    /**
     * Getter for the snowfall field
     * @return Snowfall value which belongs to this measurement
     */
    public double getSnowfall() {
        return sf;
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
        return c;
    }

    /**
     * Getter for the wind direction field
     * @return Wind direction value which belongs to this measurement
     */
    public double getWindDirection() {
        return wD;
    }
}
