import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;

/**
 * The SaxHandler class parses through the given XML document using a SAX handler and stores the data from the
 * weather-stations in a JSON document using variables. While parsing, it also corrects missing data and erroneous
 * temperature values using methods from the DataCorrection class.
 *
 * @author Matthijs van der Wal, Anne de Graaff, Nick Scholma
 * @version 1.1
 * @since 1-2-2020
 */
public class SAXHandler extends DefaultHandler {

    private HashMap<Integer, Weatherstation> weatherstations = new HashMap<Integer, Weatherstation>();
    private Weatherstation weatherstation = null;
    private WeatherMeasurement weatherMeasurement = null;
    private ArrayList<Boolean> correctData = null;
    private ArrayList<WeatherMeasurement> allMeasurements = new ArrayList<>();
    private String elementValue;

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
     * @throws SAXException An exception indicating that an error has occurred during the SAX parsing process
     */
    @Override
    public void startDocument() throws SAXException {
        correctData = new ArrayList<>();
    }

    /**
     * Method which merges all the data into one JSON file.
     * @throws SAXException An exception indicating that an error has occurred during the SAX parsing process
     */
    @Override
    public void endDocument() throws SAXException {
        mergeData.adjustData(weatherstations);         //Merges all the data into one JSON file
    }

    /**
     * Method which defines the element in the XML document where the SAX handler will begin to parse.
     * @param uri Universal resource identifier
     * @param localname Local name
     * @param qName Qualified name
     * @param attributes Attributes attached to the element
     * @throws SAXException An exception indicating that an error has occurred during the SAX parsing process
     */
    @Override
    public void startElement(String uri, String localname, String qName, Attributes attributes) throws SAXException {
    }

    /**
     * Method which defines the element in the XML document where the SAX handler will end its parsing, and which also
     * runs the DataCorrection methods on the collected data.
     * @param uri Universal resource identifier
     * @param localName Local name
     * @param qName Qualified name
     */
    @Override
    public void endElement(String uri, String localName, String qName) {
        try {
            if (qName.equalsIgnoreCase("STN")) {
                int stn = (Integer.valueOf(elementValue));
                Weatherstation existingWeatherStation = null;
                if (weatherstations.containsKey(stn)) { //this if is a failsafe for if the same weather station is parsed through more than once. If all goes well the code should never reach within this if
                    existingWeatherStation = weatherstations.get(stn);
                    weatherstation = existingWeatherStation;                //The existing weather station is the weather station which the measurement belongs to
                } else {
                    //If the STN is not known already:
                    weatherstation = new Weatherstation(stn);               //Make a new Weatherstation
                    weatherstations.put(weatherstation.stn, weatherstation);                   //Add it to the list
                }
                weatherMeasurement = new WeatherMeasurement();              //Make a new WeatherMeasurement
                weatherMeasurement.setStn(Integer.valueOf(elementValue));   //Set the Stn variable of the WeatherMeasurement to the corresponding one.
                allMeasurements = weatherstations.get(stn).getWeatherMeasurements();
                if(allMeasurements.size()>=30){ //if there are 30 or more measurements
                    weatherstations.get(stn).removeOldestValue(); //remove the oldest one, ensuring our list stays at 30 values
                }


            } else if (qName.equalsIgnoreCase("DATE")) {
                weatherMeasurement.setDate(elementValue);                   //Set the Date variable of the WeatherMeasurement to the corresponding one.
            } else if (qName.equalsIgnoreCase("TIME")) {
                weatherMeasurement.setTime(elementValue);                   //Set the Time variable of the WeatherMeasurement to the corresponding one.
            } else if (qName.equalsIgnoreCase("TEMP")) {
                if (elementValue.equals("\t\t")) { //"\t\t means that the value is empty, and thus is missing"
                    correctData.add(false);
                } else {
                    correctData.add(true);
                    weatherMeasurement.setTemperature(Double.valueOf(elementValue));        //Set the Temperature variable of the weatherMeasurement to the corresponding one.
                }
            } else if (qName.equalsIgnoreCase("DEWP")) {
                if (elementValue.equals("\t\t")) {
                    correctData.add(false);
                } else {
                    correctData.add(true);
                    weatherMeasurement.setDewpoint(Double.valueOf(elementValue));           //Set the Dewpoint variable of the weatherMeasurement to the corresponding one.

                }
            } else if (qName.equalsIgnoreCase("STP")) {
                if (elementValue.equals("\t\t")) {
                    correctData.add(false);
                } else {
                    correctData.add(true);
                    weatherMeasurement.setAirPressureStationLevel(Double.valueOf(elementValue));    //Set the AirPressureStationLevel variable of the weatherMeasurement to the corresponding one.
                }
            } else if (qName.equalsIgnoreCase("SLP")) {
                if (elementValue.equals("\t\t")) {
                    correctData.add(false);
                } else {
                    correctData.add(true);
                    weatherMeasurement.setAirPressureSeaLevel(Double.valueOf(elementValue));        //Set the AirPressureSeaLevel variable of the weatherMeasurement to the corresponding one.
                }
            } else if (qName.equalsIgnoreCase("VISIB")) {
                if (elementValue.equals("\t\t")) {
                    correctData.add(false);
                } else {
                    weatherMeasurement.setVisibility(Double.valueOf(elementValue));         //Set the Visibility variable of the weatherMeasurement to the corresponding one.
                    correctData.add(true);
                }
            } else if (qName.equalsIgnoreCase("WDSP")) {
                if (elementValue.equals("\t\t")) {
                    correctData.add(false);
                } else {
                    weatherMeasurement.setWindSpeed(Double.valueOf(elementValue));          //Set the Windspeed variable of the weatherMeasurement to the corresponding one.
                    correctData.add(true);
                }
            } else if (qName.equalsIgnoreCase("PRCP")) {
                if (elementValue.equals("\t\t")) {
                    correctData.add(false);
                } else {
                    weatherMeasurement.setRainfall(Double.valueOf(elementValue));           //Set the Rainfall variable of the weatherMeasurement to the corresponding one.
                    correctData.add(true);
                }
            } else if (qName.equalsIgnoreCase("SNDP")) {
                if (elementValue.equals("\t\t")) {
                    correctData.add(false);
                } else {
                    weatherMeasurement.setSnowfall(Double.valueOf(elementValue));           //Set the Snowfall variable of the weatherMeasurement to the corresponding one.
                    correctData.add(true);
                }
            } else if (qName.equalsIgnoreCase("FRSHTT")) {
                if (elementValue.equals("\t\t")) {
                    correctData.add(false);
                } else {
                    weatherMeasurement.setFRSHTT(elementValue);                             //Set the FRSHTT variable of the weatherMeasurement to the corresponding one.
                    correctData.add(true);
                }
            } else if (qName.equalsIgnoreCase("CLDC")) {
                if (elementValue.equals("\t\t")) {
                    correctData.add(false);
                } else {
                    weatherMeasurement.setCloudy(Double.valueOf(elementValue));             //Set the Cloudy variable of the weatherMeasurement to the corresponding one.
                    correctData.add(true);
                }
            } else if (qName.equalsIgnoreCase("WNDDIR")) {
                if (elementValue.equals("\t\t")) {
                    correctData.add(false);
                } else {
                    weatherMeasurement.setWindDirection(Double.valueOf(elementValue));       //Set the WindDirection variable of the weatherMeasurement to the corresponding one.
                    correctData.add(true);
                }
            }
            else if (qName.equalsIgnoreCase("MEASUREMENT")) {
                int aantal = 0;
                for (Boolean correctDataSingle : correctData) { //this loop in combination with the switch within ensure that the data correction methods will run, and only on the missing values
                    switch (aantal) { //In this switch the data correction method is run on values if they are empty
                        case 0:
                            //TEMP
                            if (!correctDataSingle) { //if the value is missing
                                ArrayList<Double> list = new ArrayList<>(); //make a new ArrayList
                                for (WeatherMeasurement singleMeasurements : allMeasurements) {
                                    list.add(singleMeasurements.getTemperature()); //fill that ArrayList with the existing values
                                }
                                Double fixedValue = dataCorrection.correctTheData(list); //extrapolate the missing value based on the existing ones
                                weatherMeasurement.setTemperature(fixedValue); //add the new extrapolated value to the weatherMeasurement
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
                                ArrayList<Double> list = new ArrayList<>();
                                for (WeatherMeasurement singleMeasurements : allMeasurements) {
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
                                weatherMeasurement.setFRSHTT(frshtt); //in the case of the FRSHTT variable extrapolation is not possible, because it's a binary value where every bit has an individual meaning. As such the previous value for FRSHTT is used insead
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
                if (allMeasurements.size() >= 30) { //if we have at least 30 measurements:
                    ArrayList<Double> temperatureList = new ArrayList<>(); //make a new ArrayList
                    for (WeatherMeasurement singleMeasurement : allMeasurements) {
                        Double temp = singleMeasurement.getTemperature();
                        temperatureList.add(temp); //add all temperature values collected thus far in the new ArrayList
                    }
                    Double correctedValue = dataCorrection.correctTemperature(temperatureList, temperatureList.get(temperatureList.size() - 1)); //run the temperature specific correction on the last element in the new list
                    weatherMeasurement.setTemperature(correctedValue); //add the (possibly corrected) value to the weatherMeasurement
                    temperatureList.clear(); //empty the temperature list
                }
                weatherstation.addWeatherMeasurement(weatherMeasurement); //add the whole measurement to the weatherstation
                correctData.clear(); //empty the list of booleans used to check if data is correct
            }
        } catch (Exception e) {
        }
    }


    /**
     * ????
     * @param ch List of characters
     * @param start The start position in the character array
     * @param length The number of characters to use from the character array
     * @throws SAXException When something goes wrong during the passing process a SAXException well be thrown
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
}
