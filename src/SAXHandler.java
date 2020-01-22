

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.crypto.dom.*;

public class SAXHandler extends DefaultHandler {

    private ArrayList<Weatherstation> weatherstations = new ArrayList<Weatherstation>();
    private Weatherstation weatherstation = null;
    private WeatherMeasurement weatherMeasurement = null;
    private String elementValue;
    String json = null;
    private int timeTillPrint = 10;
    private int timeTillPrintCounter = 1;
    private Thread merger;
    private MergeData mergeData;

    public SAXHandler(Thread merger, MergeData mergeData) {
        this.merger = merger;
        this.mergeData = mergeData;
    }

    @Override
    public void startDocument() throws SAXException {
//         weatherstations = new ArrayList<Weatherstation>();
    }

    @Override
    public void endDocument() throws SAXException {
//        if(timeTillPrintCounter >= timeTillPrint) {
//            json = new Gson().toJson(weatherstations);  //Makes JSON file from ArrayList (TEST PURPOSES)
//            mergeData.printIt();                        //Print JSON file from above (TEST PURPOSES)
//            timeTillPrintCounter = 1;                   //Counter for call to print and merge
//        }
//        timeTillPrintCounter++;
        mergeData.adjustData("Add", weatherstations);         //Merges all the data into one JSON file
    }


    @Override
    public void startElement(String uri, String localname, String qName, Attributes attributes) throws SAXException {
//        if (qName.equalsIgnoreCase("MEASUREMENT")) {
//            weatherstation = new Weatherstation();
//        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        try {
            if (qName.equalsIgnoreCase("MEASUREMENT")) {
                weatherstation.addWeatherMeasurement(weatherMeasurement);   //When whole measurement has been read, add it to the corresponding weatherstation.
            } else if (qName.equalsIgnoreCase("STN")) {        //If it reaches STN do the following tasks:
                int stn = (Integer.valueOf(elementValue));                  //Make variable of corresponding value
                boolean exists = false;                                     //Variable to check if STN already exist within a known weatherstation
                Weatherstation existingWeatherStation = null;               //Variable to hold the already known weatherstation
                for (Weatherstation weatherstation:weatherstations) {       //Loop trough known weatherstations
                    if(weatherstation.getStn() == stn) {                    //If a the stn of the measurement is already known:
                        exists = true;                                      //Weatherstation exists = true
                        existingWeatherStation = weatherstation;            //The corresponding weatherstation is the one which is found.
                    }
                }
                if(!exists) {                                               //If the STN is not known already:
                    weatherstation = new Weatherstation(stn);               //Make a new weatherstation
                    weatherstations.add(weatherstation);                    //Add it to the list
                } else {                                                    //If the STN already known by the system:
                    weatherstation = existingWeatherStation;                //The existing weatherstation is the weatherstation which the measurement belongs to
                }
                weatherMeasurement = new WeatherMeasurement();              //Make a new weathermeasurement
                weatherMeasurement.setStn(Integer.valueOf(elementValue));   //Set the Stn variable of the weatherMeasurement to the corresponding one.
            }
             else if (qName.equalsIgnoreCase("DATE")) {
                weatherMeasurement.setDate(elementValue);                   //Set the Date variable of the weatherMeasurement to the corresponding one.
            } else if (qName.equalsIgnoreCase("TIME")) {
                weatherMeasurement.setTime(elementValue);                   //Set the Time variable of the weatherMeasurement to the corresponding one.
            } else if (qName.equalsIgnoreCase("TEMP")) {
                weatherMeasurement.setTemperature(Double.valueOf(elementValue));        //Set the Temperature variable of the weatherMeasurement to the corresponding one.
            } else if (qName.equalsIgnoreCase("DEWP")) {
                weatherMeasurement.setDewpoint(Double.valueOf(elementValue));           //Set the Dewpoint variable of the weatherMeasurement to the corresponding one.
            } else if (qName.equalsIgnoreCase("STP")) {
                weatherMeasurement.setAirPressureStationLevel(Double.valueOf(elementValue));    //Set the AirPressureStationLevel variable of the weatherMeasurement to the corresponding one.
            } else if (qName.equalsIgnoreCase("SLP")) {
                weatherMeasurement.setAirPressureSeaLevel(Double.valueOf(elementValue));        //Set the AirPressureSeaLevel variable of the weatherMeasurement to the corresponding one.
            } else if (qName.equalsIgnoreCase("VISIB")) {
                weatherMeasurement.setVisibility(Double.valueOf(elementValue));         //Set the Visibility variable of the weatherMeasurement to the corresponding one.
            } else if (qName.equalsIgnoreCase("WDSP")) {
                weatherMeasurement.setWindSpeed(Double.valueOf(elementValue));          //Set the Windspeed variable of the weatherMeasurement to the corresponding one.
            } else if (qName.equalsIgnoreCase("PRCP")) {
                weatherMeasurement.setRainfall(Double.valueOf(elementValue));           //Set the Rainfall variable of the weatherMeasurement to the corresponding one.
            } else if (qName.equalsIgnoreCase("SNDP")) {
                weatherMeasurement.setSnowfall(Double.valueOf(elementValue));           //Set the Snowfall variable of the weatherMeasurement to the corresponding one.
            } else if (qName.equalsIgnoreCase("FRSHTT")) {
                weatherMeasurement.setFRSHTT(elementValue);                             //Set the FRSHTT variable of the weatherMeasurement to the corresponding one.
            } else if (qName.equalsIgnoreCase("CLDC")) {
                weatherMeasurement.setCloudy(Double.valueOf(elementValue));             //Set the Cloudy variable of the weatherMeasurement to the corresponding one.
            } else if (qName.equalsIgnoreCase("WNDDIR")) {
                weatherMeasurement.setWindDirection(Short.valueOf(elementValue));       //Set the WindDirection variable of the weatherMeasurement to the corresponding one.
            }
//            for (WeatherMeasurement weatherMeasurement:
//                    weatherstation.getWeatherMeasurements()) {
//                //System.out.println("ding: " +weatherMeasurement.getStn() + "andere ding:" + weatherstation.stn);
//
//                System.out.println(json);
//            }
        }
        catch (NumberFormatException e){
            //System.out.println("Value missing");
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        elementValue = new String(ch, start, length);
    }

    public List<Weatherstation> getWeatherstations() {
        return weatherstations;
    }

    public String getJson() {
        return json;
    }
}
