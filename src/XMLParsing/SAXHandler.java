package XMLParsing;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.crypto.dom.*;

public class SAXHandler extends DefaultHandler {

    private List<XMLParsing.Weatherstation> weatherstations = null;
    private XMLParsing.Weatherstation weatherstation = null;
    private String elementValue;

    @Override
    public void startDocument() throws SAXException {
        weatherstations = new ArrayList<XMLParsing.Weatherstation>();
    }

    @Override
    public void startElement(String uri, String localname, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("MEASUREMENT")) {
            weatherstation = new XMLParsing.Weatherstation();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        try {
            if (qName.equalsIgnoreCase("MEASUREMENT")) {
                weatherstations.add(weatherstation);
            } else if (qName.equalsIgnoreCase("STN")) {
                weatherstation.setStn(Integer.valueOf(elementValue));
            } else if (qName.equalsIgnoreCase("DATE")) {
                weatherstation.setDate(elementValue);
            } else if (qName.equalsIgnoreCase("TIME")) {
                weatherstation.setTime(elementValue);
            } else if (qName.equalsIgnoreCase("TEMP")) {
                weatherstation.setTemperature(Double.valueOf(elementValue));
            } else if (qName.equalsIgnoreCase("DEWP")) {
                weatherstation.setDewpoint(Double.valueOf(elementValue));
            } else if (qName.equalsIgnoreCase("STP")) {
                weatherstation.setAirPressureStationLevel(Double.valueOf(elementValue));
            } else if (qName.equalsIgnoreCase("SLP")) {
                weatherstation.setAirPressureSeaLevel(Double.valueOf(elementValue));
            } else if (qName.equalsIgnoreCase("VISIB")) {
                weatherstation.setVisibility(Double.valueOf(elementValue));
            } else if (qName.equalsIgnoreCase("WDSP")) {
                weatherstation.setWindSpeed(Double.valueOf(elementValue));
            } else if (qName.equalsIgnoreCase("PRCP")) {
                weatherstation.setRainfall(Double.valueOf(elementValue));
            } else if (qName.equalsIgnoreCase("SNDP")) {
                weatherstation.setSnowfall(Double.valueOf(elementValue));
            } else if (qName.equalsIgnoreCase("FRSHTT")) {
                weatherstation.setFRSHTT(elementValue);
            } else if (qName.equalsIgnoreCase("CLDC")) {
                weatherstation.setCloudy(Double.valueOf(elementValue));
            } else if (qName.equalsIgnoreCase("WNDDIR")) {
                weatherstation.setWindDirection(Short.valueOf(elementValue));
            }
            System.out.println(getWeatherstations());
        }
        catch (NumberFormatException e){
            System.out.println("Value missing");
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        elementValue = new String(ch, start, length);
    }

    public List<XMLParsing.Weatherstation> getWeatherstations() {
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
