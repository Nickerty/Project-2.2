import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;

/**
 * The SaxHandler class parses through the given XML document using a SAX handler and stores the data from the
 * weather-stations in the document in variables. It also corrects missing data and erroneous temperature values
 * using methods from the DataCorrection class.
 *
 * @author Matthijs van der Wal, Anne de Graaff, Nick Scholma
 * @version 1.0
 * @since 24-1-2020
 */
public class SAXHandler extends DefaultHandler {

    private HashMap<Integer, Weatherstation> weatherstations = new HashMap<Integer, Weatherstation>();
    private Weatherstation weatherstation = null;
    private WeatherMeasurement weatherMeasurement = null;
    private ArrayList<Boolean> correctData = null;
    private ArrayList<WeatherMeasurement> allMeasurements = new ArrayList<>();
    ArrayList<Double> temperatureList = new ArrayList<>();
    private String elementValue;
    private boolean delete = false;
    String json = null; //TODO Private toevoegen?

    private MergeData mergeData;
    private DataCorrection dataCorrection = new DataCorrection();

    /**
     * Constructor for the SAXHandler class
     * @param mergeData Instance of the class MergeData which is run by the thread: merger.
     */
    public SAXHandler(MergeData mergeData) {
        this.mergeData = mergeData;
    }

    /**
     * Method which initialises the correctData field as an empty ArrayList
     * @throws SAXException When something goes wrong during the parsing process a SAXException will be thrown
     */
    @Override
    public void startDocument() throws SAXException {
        correctData = new ArrayList<>();
    }

    /**
     * Method which merges all the data into one JSON file.
     * @throws SAXException When something goes wrong during the pasing process a SAXException well be thrown
     */
    @Override
    public void endDocument() throws SAXException {
//
//            json = new Gson().toJson(weatherstations);  //Makes JSON file from ArrayList (TEST PURPOSES)
//            mergeData.printIt();                        //Print JSON file from above (TEST PURPOSES)
//            timeTillPrintCounter = 1;                   //Counter for call to print and merge
//        }
//        timeTillPrintCounter++;
        mergeData.adjustData(weatherstations);         //Merges all the data into one JSON file
    }

    /**
     * Method which defines the element where the SAX handler will begin to parse.
     * @param uri Universal resource identifier
     * @param localname Local name
     * @param qName Qualified name
     * @param attributes Attributes attached to the element
     * @throws SAXException When something goes wrong during the pasing process a SAXException well be thrown
     */
    @Override
    public void startElement(String uri, String localname, String qName, Attributes attributes) throws SAXException {
    }

    /**
     * Method which defines the element where the SAX handler will end its parsing, and which also runs the datacorrection
     * methods on the collected data.
     * @param uri Universal resource identifier
     * @param localName Local name
     * @param qName Qualified name
     */
    @Override
    public void endElement(String uri, String localName, String qName) {
        try {
            if (qName.equalsIgnoreCase("STN")) {
                int stn = (Integer.valueOf(elementValue));
                boolean exists = false; //TODO de boolean exists wordt niet meer gebruikt?
                Weatherstation existingWeatherStation = null;
                // hier komt de code wel
                if (weatherstations.containsKey(stn)) {
                    //TODO De code komt hier nooit
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
//                ArrayList<WeatherMeasurement> allMeasurements = weatherstations.get(stn).getSpecificNumberOfWeatherMeasurements(30);
                allMeasurements = weatherstations.get(stn).getWeatherMeasurements();
                //System.out.println("class ding size is " + weatherstations.get(stn).getWeatherMeasurements().size());
                //System.out.println("array ding size is " + allMeasurements.size());
                if(allMeasurements.size()>=30){
                    weatherstations.get(stn).removeOldestValue();
                    //System.out.println("Reached 30 values so the oldest value will be removed");
                }


            } else if (qName.equalsIgnoreCase("DATE")) {
                weatherMeasurement.setDate(elementValue);                   //Set the Date variable of the weatherMeasurement to the corresponding one.
            } else if (qName.equalsIgnoreCase("TIME")) {
                weatherMeasurement.setTime(elementValue);                   //Set the Time variable of the weatherMeasurement to the corresponding one.
            } else if (qName.equalsIgnoreCase("TEMP")) {
                if (elementValue.equals("\t\t")) {
                    //TODO De code komt hier nooit, waardoor er nooit bij de correctie gekomen kan worden voor temp
                    correctData.add(false);
                } else {
                    correctData.add(true);
                    weatherMeasurement.setTemperature(Double.valueOf(elementValue));        //Set the Temperature variable of the weatherMeasurement to the corresponding one.
                }
            } else if (qName.equalsIgnoreCase("DEWP")) {
                if (elementValue.equals("\t\t")) {
                    //System.out.println("FUCK dewp"); //HIER KOMT DE CODE WEL WAT
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
            else if (qName.equalsIgnoreCase("MEASUREMENT")) {
                int aantal = 0;
                if (allMeasurements.size() > 1) {
                    //System.out.println(allMeasurements.size());
                }
                for (Boolean correctDataSingle : correctData) {
                    switch (aantal) { //In this switch the data correction method is run on values if they are empty
                        case 0:
                            //TEMP
                            if (!correctDataSingle) {
//                                System.out.println("YO WE ZIJN HIER");
                                ArrayList<Double> list = new ArrayList<>();
                                for (WeatherMeasurement singleMeasurements : allMeasurements) {
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
                                for (WeatherMeasurement singleMeasurements : allMeasurements) {
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
                                for (WeatherMeasurement singleMeasurements : allMeasurements) {
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
                                for (WeatherMeasurement singleMeasurements : allMeasurements) {
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
                                for (WeatherMeasurement singleMeasurements : allMeasurements) {
                                    list.add(singleMeasurements.getVisibility());
                                }
                                Double fixedValue = dataCorrection.correctTheData(list);
                                weatherMeasurement.setVisibility(fixedValue);
                            }
                            break;
                        case 5:
                            //WDSP
                            if (!correctDataSingle) {
                                //System.out.println("OH SHIT EEN FOUT WANT DE FILE IS " + weatherMeasurement.getWindSpeed());
                                ArrayList<Double> list = new ArrayList<>();
                                for (WeatherMeasurement singleMeasurements : allMeasurements) {
                                    list.add(singleMeasurements.getWindSpeed());
                                }
                                Double fixedValue = dataCorrection.correctTheData(list);
                                weatherMeasurement.setWindSpeed(fixedValue);
                                //System.out.println("SHIT IS FIXED WANT HET IS NU " + weatherMeasurement.getWindSpeed() + " (hoort " + fixedValue + " te zijn)");
                            }
                            break;
                        case 6:
                            //PRCP
                            if (!correctDataSingle) {
                                ArrayList<Double> list = new ArrayList<>();
                                for (WeatherMeasurement singleMeasurements : allMeasurements) {
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
                                for (WeatherMeasurement singleMeasurements : allMeasurements) {
                                    list.add(singleMeasurements.getSnowfall());
                                }
                                Double fixedValue = dataCorrection.correctTheData(list);
                                weatherMeasurement.setSnowfall(fixedValue);
                            }
                            break;
                        case 8:
                            //FRSHTT
                            if (!correctDataSingle) {
                                String frshtt = allMeasurements.subList(allMeasurements.size() - 2, allMeasurements.size() - 1).get(0).getFRSHTT();
                                weatherMeasurement.setFRSHTT(frshtt);
                            }
                            break;
                        case 9:
                            //CLDC
                            if (!correctDataSingle) {
                                ArrayList<Double> list = new ArrayList<>();
                                for (WeatherMeasurement singleMeasurements : allMeasurements) {
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
                                for (WeatherMeasurement singleMeasurements : allMeasurements) {
                                    list.add(singleMeasurements.getWindDirection());
                                }
                                Double fixedValue = dataCorrection.correctTheData(list);
                                weatherMeasurement.setWindDirection(fixedValue);
                            }
                            break;
                    }
                    aantal++;
                }
                weatherstation.addWeatherMeasurement(weatherMeasurement);
                temperatureList.add(weatherMeasurement.getTemperature());
                if (temperatureList.size() == 31) {
//                    System.out.println(temperatureList);
                    for (Double temp : temperatureList) {
                        dataCorrection.correctTemperature(temperatureList, temp);
                    }
                    temperatureList.clear();
                }
                correctData.clear();
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


    /**
     * ????
     * @param ch List of characters
     * @param start The start position in the character array
     * @param length The number of characters to use from the character array
     * @throws SAXException When something goes wrong during the pasing process a SAXException well be thrown
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        elementValue = new String(ch, start, length);
    }

    /**
     * Getter for the weatherstations field.
     * @return a Hashmap containing all known weatherstations, mapped by their ID.
     */
    public HashMap<Integer, Weatherstation> getWeatherstations() {
        return weatherstations;
    }

//    /**
//     * Getter for the json field.
//     * @return A string containing weather data
//     */
//    public String getJson() {
//        return json;
//    }
}
