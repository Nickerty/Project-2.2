package XMLParsing;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {

    private List<Weatherstation> weatherstations = null;
    private Weatherstation weatherstation = null;
    private String elementValue;

    @Override
    public void startDocument() throws SAXException {
        weatherstations = new ArrayList<Weatherstation>();
    }

    @Override
    public void startElement(String uri, String localname, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("MEASUREMENT")) {
            weatherstation = new Weatherstation();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("MEASUREMENT")) {
            weatherstations.add(weatherstation);
        }
        if (qName.equalsIgnoreCase("STN")) {
            weatherstation.setStn(Integer.valueOf(elementValue));
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
