

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.crypto.dom.*;

public class SAXHandler extends DefaultHandler {

    private HashMap<Integer, Weatherstation> weatherstations = new HashMap<Integer, Weatherstation>();
    private Weatherstation weatherstation = null;
    private WeatherMeasurement weatherMeasurement = null;
    private ArrayList<Boolean> correctData = null;
    private String elementValue;
    String json = null;
    private int timeTillPrint = 10;
    private int timeTillPrintCounter = 1;
    private Thread merger;
    private MergeData mergeData;
    private DataCorrection dataCorrection = new DataCorrection();

    public SAXHandler(Thread merger, MergeData mergeData) {
        this.merger = merger;
        this.mergeData = mergeData;
    }

    @Override
    public void startDocument() throws SAXException {
//         weatherstations = new ArrayList<Weatherstation>();
        correctData = new ArrayList<>();
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
    public void endElement(String uri, String localName, String qName) {
        try {

            if (qName.equalsIgnoreCase("MEASUREMENT")) {
                weatherstation.addWeatherMeasurement(weatherMeasurement);
            } else if (qName.equalsIgnoreCase("STN")) {
                int stn = (Integer.valueOf(elementValue));
                boolean exists = false;
                Weatherstation existingWeatherStation = null;
                if (weatherstations.containsKey(stn)) {
                    exists = true;
                    existingWeatherStation = weatherstations.get(stn);
                    weatherstation = existingWeatherStation;                //The existing weatherstation is the weatherstation which the measurement belongs to
                } else {
                    //If the STN is not known already:
                    weatherstation = new Weatherstation(stn);               //Make a new weatherstation
                    weatherstations.put(weatherstation.stn, weatherstation);                   //Add it to the list
                }
                weatherMeasurement = new WeatherMeasurement();              //Make a new weathermeasurement
                weatherMeasurement.setStn(Integer.valueOf(elementValue));   //Set the Stn variable of the weatherMeasurement to the corresponding one.
                int aantal = 0;
                ArrayList<WeatherMeasurement> allMeasurements = weatherstations.get(stn).getSpecificNumberOfWeatherMeasurements(30);
                for (Boolean correctDataSingle : correctData) {
                    switch(aantal) {
                        case 0:
                            //TEMP
                            if (!correctDataSingle) {
                                ArrayList<Double> list = new ArrayList<>();
                                for (WeatherMeasurement singleMeasurements:allMeasurements) {
                                    list.add(singleMeasurements.getTemperature());
                                }
                                Double fixedValue = dataCorrection.correctTheData(list);
                                weatherMeasurement.setTemperature(fixedValue);
                            }
                            break;
                        case 1:
                            //DEWP
                            if (!correctDataSingle) {
                                ArrayList<Double> list = new ArrayList<>();
                                for (WeatherMeasurement singleMeasurements:allMeasurements) {
                                    list.add(singleMeasurements.getDewpoint());
                                }
                                Double fixedValue = dataCorrection.correctTheData(list);
                                weatherMeasurement.setDewpoint(fixedValue);
                            }
                            break;
                        case 2:
                            //STP
                            if (!correctDataSingle) {
                                ArrayList<Double> list = new ArrayList<>();
                                for (WeatherMeasurement singleMeasurements:allMeasurements) {
                                    list.add(singleMeasurements.getAirPressureStationLevel());
                                }
                                Double fixedValue = dataCorrection.correctTheData(list);
                                weatherMeasurement.setAirPressureStationLevel(fixedValue);
                            }
                            break;
                        case 3:
                            //SLP
                            if (!correctDataSingle) {
                                ArrayList<Double> list = new ArrayList<>();
                                for (WeatherMeasurement singleMeasurements:allMeasurements) {
                                    list.add(singleMeasurements.getAirPressureSeaLevel());
                                }
                                Double fixedValue = dataCorrection.correctTheData(list);
                                weatherMeasurement.setAirPressureSeaLevel(fixedValue);
                            }
                            break;
                        case 4:
                            //VISIB
                            if (!correctDataSingle) {
                                ArrayList<Double> list = new ArrayList<>();
                                for (WeatherMeasurement singleMeasurements:allMeasurements) {
                                    list.add(singleMeasurements.getVisibility());
                                }
                                Double fixedValue = dataCorrection.correctTheData(list);
                                weatherMeasurement.setVisibility(fixedValue);
                            }
                            break;
                        case 5:
                            //WDSP
                            if (!correctDataSingle) {
                                ArrayList<Double> list = new ArrayList<>();
                                for (WeatherMeasurement singleMeasurements:allMeasurements) {
                                    list.add(singleMeasurements.getWindSpeed());
                                }
                                Double fixedValue = dataCorrection.correctTheData(list);
                                weatherMeasurement.setWindSpeed(fixedValue);
                            }
                            break;
                        case 6:
                            //PRCP
                            if (!correctDataSingle) {
                                ArrayList<Double> list = new ArrayList<>();
                                for (WeatherMeasurement singleMeasurements:allMeasurements) {
                                    list.add(singleMeasurements.getRainfall());
                                }
                                Double fixedValue = dataCorrection.correctTheData(list);
                                weatherMeasurement.setRainfall(fixedValue);
                            }
                            break;
                        case 7:
                            //SNDP
                            if (!correctDataSingle) {
                                ArrayList<Double> list = new ArrayList<>();
                                for (WeatherMeasurement singleMeasurements:allMeasurements) {
                                    list.add(singleMeasurements.getSnowfall());
                                }
                                Double fixedValue = dataCorrection.correctTheData(list);
                                weatherMeasurement.setSnowfall(fixedValue);
                            }
                            break;
                        case 8:
                            //FRSHTT
                            if (!correctDataSingle) {
                                String frshtt = allMeasurements.subList(allMeasurements.size()-2, allMeasurements.size()-1).get(0).getFRSHTT();
                                weatherMeasurement.setFRSHTT(frshtt);
                            }
                            break;
                        case 9:
                            //CLDC
                            if (!correctDataSingle) {
                                ArrayList<Double> list = new ArrayList<>();
                                for (WeatherMeasurement singleMeasurements:allMeasurements) {
                                    list.add(singleMeasurements.getCloudy());
                                }
                                Double fixedValue = dataCorrection.correctTheData(list);
                                weatherMeasurement.setCloudy(fixedValue);
                            }
                            break;
                        case 10:
                            //WINDDIR
                            if (!correctDataSingle) {
                                ArrayList<Double> list = new ArrayList<>();
                                for (WeatherMeasurement singleMeasurements:allMeasurements) {
                                    list.add(singleMeasurements.getWindDirection());
                                }
                                Double fixedValue = dataCorrection.correctTheData(list);
                                weatherMeasurement.setWindDirection(fixedValue);
                            }
                            break;
                    }
                    aantal++;
                }
                for (WeatherMeasurement weatherMeasurement : weatherstation.getWeatherMeasurements()) {

                }

            } else if (qName.equalsIgnoreCase("DATE")) {
                weatherMeasurement.setDate(elementValue);                   //Set the Date variable of the weatherMeasurement to the corresponding one.
            } else if (qName.equalsIgnoreCase("TIME")) {
                weatherMeasurement.setTime(elementValue);                   //Set the Time variable of the weatherMeasurement to the corresponding one.
            } else if (qName.equalsIgnoreCase("TEMP")) {
                if (elementValue.equals("\t\t")) {
                    //System.out.println("FUCK temp");
                    correctData.add(false);
                } else {
                    correctData.add(true);
                    weatherMeasurement.setTemperature(Double.valueOf(elementValue));        //Set the Temperature variable of the weatherMeasurement to the corresponding one.
                }
            } else if (qName.equalsIgnoreCase("DEWP")) {
                if (elementValue.equals("\t\t")) {
                    //System.out.println("FUCK dewp");
                    correctData.add(false);
                } else {
                    correctData.add(true);
                    weatherMeasurement.setDewpoint(Double.valueOf(elementValue));           //Set the Dewpoint variable of the weatherMeasurement to the corresponding one.

                }
            } else if (qName.equalsIgnoreCase("STP")) {
                if (elementValue.equals("\t\t")) {
                    //System.out.println("FUCK stp");
                    correctData.add(false);
                } else {
                    correctData.add(true);
                    weatherMeasurement.setAirPressureStationLevel(Double.valueOf(elementValue));    //Set the AirPressureStationLevel variable of the weatherMeasurement to the corresponding one.
                }
            } else if (qName.equalsIgnoreCase("SLP")) {
                if (elementValue.equals("\t\t")) {
                    //System.out.println("FUCK slp");
                    correctData.add(false);
                } else {
                    correctData.add(true);
                    weatherMeasurement.setAirPressureSeaLevel(Double.valueOf(elementValue));        //Set the AirPressureSeaLevel variable of the weatherMeasurement to the corresponding one.
                }
            } else if (qName.equalsIgnoreCase("VISIB")) {
                if (elementValue.equals("\t\t")) {
                    //System.out.println("FUCK visib");
                    correctData.add(false);
                } else {
                    weatherMeasurement.setVisibility(Double.valueOf(elementValue));         //Set the Visibility variable of the weatherMeasurement to the corresponding one.
                    correctData.add(true);
                }
            } else if (qName.equalsIgnoreCase("WDSP")) {
                if (elementValue.equals("\t\t")) {
                    //System.out.println("FUCK wdsp");
                    correctData.add(false);
                } else {
                    weatherMeasurement.setWindSpeed(Double.valueOf(elementValue));          //Set the Windspeed variable of the weatherMeasurement to the corresponding one.
                    correctData.add(true);
                }
            } else if (qName.equalsIgnoreCase("PRCP")) {
                if (elementValue.equals("\t\t")) {
                    //System.out.println("FUCK prcp");
                    correctData.add(false);
                } else {
                    weatherMeasurement.setRainfall(Double.valueOf(elementValue));           //Set the Rainfall variable of the weatherMeasurement to the corresponding one.
                    correctData.add(true);
                }
            } else if (qName.equalsIgnoreCase("SNDP")) {
                if (elementValue.equals("\t\t")) {
                    //System.out.println("FUCK sndp");
                    correctData.add(false);
                } else {
                    weatherMeasurement.setSnowfall(Double.valueOf(elementValue));           //Set the Snowfall variable of the weatherMeasurement to the corresponding one.
                    correctData.add(true);
                }
            } else if (qName.equalsIgnoreCase("FRSHTT")) {
                if (elementValue.equals("\t\t")) {
                    //System.out.println("FUCK frshttt");
                    correctData.add(false);
                } else {
                    weatherMeasurement.setFRSHTT(elementValue);                             //Set the FRSHTT variable of the weatherMeasurement to the corresponding one.
                    correctData.add(true);
                }
            } else if (qName.equalsIgnoreCase("CLDC")) {
                if (elementValue.equals("\t\t")) {
                    //System.out.println("FUCK cloud");
                    correctData.add(false);
                } else {

                    weatherMeasurement.setCloudy(Double.valueOf(elementValue));             //Set the Cloudy variable of the weatherMeasurement to the corresponding one.
                    correctData.add(true);
                }
            } else if (qName.equalsIgnoreCase("WNDDIR")) {
                if (elementValue.equals("\t\t")) {
                    //System.out.println("FUCK wind");
                    correctData.add(false);
                } else {
                    weatherMeasurement.setWindDirection(Double.valueOf(elementValue));       //Set the WindDirection variable of the weatherMeasurement to the corresponding one.
                    correctData.add(true);
                }
            }
//            for (WeatherMeasurement weatherMeasurement:
//                    weatherstation.getWeatherMeasurements()) {
//                //System.out.println("ding: " +weatherMeasurement.getStn() + "andere ding:" + weatherstation.stn);
//
//                System.out.println(json);
//            }
        } catch (Exception e) {

        }
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        elementValue = new String(ch, start, length);
    }

    public HashMap<Integer, Weatherstation> getWeatherstations() {
        return weatherstations;
    }

    public String getJson() {
        return json;
    }
}
