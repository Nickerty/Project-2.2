

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.crypto.dom.*;

public class SAXHandler extends DefaultHandler {

    private List<Weatherstation> weatherstations = null;
    private Weatherstation weatherstation = null;
    private WeatherMeasurement weatherMeasurement = null;
    private String elementValue;
    @Override
    public void startDocument() throws SAXException {
        weatherstations = new ArrayList<Weatherstation>();
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
                 weatherstation.addWeatherMeasurement(weatherMeasurement);
            } else if (qName.equalsIgnoreCase("STN")) {
                int stn = (Integer.valueOf(elementValue));
                boolean exists = false;
                Weatherstation existingWeatherStation = null;
                for (Weatherstation weatherstation:weatherstations) {
                    if(weatherstation.getStn() == stn) {
                        exists = true;
                        existingWeatherStation = weatherstation;
                    }
                }
                if(!exists) {
                    weatherstation = new Weatherstation(stn);
                    weatherstations.add(weatherstation);
                } else {
                    weatherstation = existingWeatherStation;
                }
                weatherMeasurement = new WeatherMeasurement();
                weatherMeasurement.setStn(Integer.valueOf(elementValue));
            }
             else if (qName.equalsIgnoreCase("DATE")) {
                weatherMeasurement.setDate(elementValue);
            } else if (qName.equalsIgnoreCase("TIME")) {
                weatherMeasurement.setTime(elementValue);
            } else if (qName.equalsIgnoreCase("TEMP")) {
                weatherMeasurement.setTemperature(Double.valueOf(elementValue));
            } else if (qName.equalsIgnoreCase("DEWP")) {
                weatherMeasurement.setDewpoint(Double.valueOf(elementValue));
            } else if (qName.equalsIgnoreCase("STP")) {
                weatherMeasurement.setAirPressureStationLevel(Double.valueOf(elementValue));
            } else if (qName.equalsIgnoreCase("SLP")) {
                weatherMeasurement.setAirPressureSeaLevel(Double.valueOf(elementValue));
            } else if (qName.equalsIgnoreCase("VISIB")) {
                weatherMeasurement.setVisibility(Double.valueOf(elementValue));
            } else if (qName.equalsIgnoreCase("WDSP")) {
                weatherMeasurement.setWindSpeed(Double.valueOf(elementValue));
            } else if (qName.equalsIgnoreCase("PRCP")) {
                weatherMeasurement.setRainfall(Double.valueOf(elementValue));
            } else if (qName.equalsIgnoreCase("SNDP")) {
                weatherMeasurement.setSnowfall(Double.valueOf(elementValue));
            } else if (qName.equalsIgnoreCase("FRSHTT")) {
                weatherMeasurement.setFRSHTT(elementValue);
            } else if (qName.equalsIgnoreCase("CLDC")) {
                weatherMeasurement.setCloudy(Double.valueOf(elementValue));
            } else if (qName.equalsIgnoreCase("WNDDIR")) {
                weatherMeasurement.setWindDirection(Short.valueOf(elementValue));
            }
            for (WeatherMeasurement weatherMeasurement:
                    weatherstation.getWeatherMeasurements()) {
                //System.out.println("ding: " +weatherMeasurement.getStn() + "andere ding:" + weatherstation.stn);
                String json = new Gson().toJson(weatherstations);
                System.out.println(json);
            }
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
}

//        String date = attributes.getValue("DATE");
//        String time = attributes.getValue("TIME");
//        String dew_point = attributes.getValue("DEWP");
//        String air_pressure_station = attributes.getValue("STP");
//        String air_pressure_zee = attributes.getValue("SLP");
//        String visibility = attributes.getValue("VISIB");
//        String windspeed = attributes.getValue("WDSP");
//        String rain_amount = attributes.getValue("PRCP");
//        String snow_amount = attributes.getValue("SNDP");
//        String binary_value = attributes.getValue("FRSHTT");
//        String clouds = attributes.getValue("CLDC");
//        String wind_direction = attributes.getValue("WNDDIR");

//    public SAXHandler(String filename){
//        String path = new File(filename).getAbsolutePath();
//        convertToFileURL(path);
//    }
//
//    public String convertToFileURL(String path){
//        if(File.separatorChar != '/'){
//            path = path.replace(File.separatorChar, '/');
//        }
//
//        if(!path.startsWith("/")){
//            path = "/" + path;
//        }
//        return "file:" + path;
//    }
