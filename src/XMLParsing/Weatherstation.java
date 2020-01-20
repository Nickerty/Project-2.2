package XMLParsing;

public class Weatherstation {

    private int stn;
    private String name;
    private String country;
    private double latitude;
    private double longitude;
    private double elevation;

    public int getStn(){
        return stn;
    }

    public void setStn(int stn){
        this.stn = stn;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getCountry(){
        return country;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setLatitude(double latitude){
        this.latitude =latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public double getElevation(){
        return elevation;
    }

    public void setElevation(double elevation){
        this.elevation = elevation;
    }
}
