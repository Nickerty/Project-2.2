import java.util.*;

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

    public void setStn(int stn) {
        this.stn = stn;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setDewpoint(double dewpoint) {
        this.dewpoint = dewpoint;
    }

    public void setAirPressureStationLevel(double airPressureStationLevel) {
        this.airPressureStationLevel = airPressureStationLevel;
    }

    public void setAirPressureSeaLevel(double airPressureSeaLevel) {
        this.airPressureSeaLevel = airPressureSeaLevel;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setRainfall(double rainfall) {
        this.rainfall = rainfall;
    }

    public void setSnowfall(double snowfall) {
        this.snowfall = snowfall;
    }

    public void setFRSHTT(String FRSHTT) {
        this.FRSHTT = FRSHTT;
    }

    public void setCloudy(double cloudy) {
        this.cloudy = cloudy;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public int getStn() {
        return stn;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getDewpoint() {
        return dewpoint;
    }

    public double getAirPressureStationLevel() {
        return airPressureStationLevel;
    }

    public double getAirPressureSeaLevel() {
        return airPressureSeaLevel;
    }

    public double getVisibility() {
        return visibility;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getRainfall() {
        return rainfall;
    }

    public double getSnowfall() {
        return snowfall;
    }

    public String getFRSHTT() {
        return FRSHTT;
    }

    public double getCloudy() {
        return cloudy;
    }

    public double getWindDirection() {
        return windDirection;
    }
}
