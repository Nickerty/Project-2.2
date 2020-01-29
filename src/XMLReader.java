

import java.io.*;
import java.util.Calendar;
import java.util.List;

import javax.xml.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.sql.SQLXML;
import java.util.concurrent.TimeUnit;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * The XMLReader class is a class which reads the XML file which it received. Once it has opened the file it will
 * parse it using the SAXHandler.
 *
 * @author Matthijs van der Wal, Anne de Graaff
 * @version 1.0
 * @since 24-1-2020
 */
public class XMLReader implements Runnable {
    private InputStream data;
    private MergeData mergeData;
    public static final String UTF8_BOM = "\uFEFF";
    private volatile boolean running = true;
    /**
     * Constructor for the XMLReader class
     * @param mergeData Instance of the class MergeData
     */
    public XMLReader(MergeData mergeData) {
        this.mergeData = mergeData;
    }

    /**
     * Method which will read the XML file, this part is ran parallel by different threads
     */
    @Override
    public void run() {
        try
        {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            SAXHandler saxHandler = new SAXHandler(mergeData);
            while(running) {
                String rowLines = null;
                String line = null;
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(data, "UTF-8"));
                try {
                    line = bufferedReader.readLine();
                    Boolean done = false;
                    while (!done) {
                        if (line.equals("<WEATHERDATA>")) {
                            stringBuilder.append(line);
                            done = true;
                            while (!line.equals("</WEATHERDATA>")) {
                                line = bufferedReader.readLine();
                                stringBuilder.append(line);
                            }
                        }
                        line = bufferedReader.readLine();
                    }
                } catch (NullPointerException NE) {
                    System.out.println("oef");
                }
                String finalString = stringBuilder.toString();
                InputSource saxInputSource = new InputSource(new StringReader(finalString));
                try {
                    saxParser.parse(saxInputSource, saxHandler);
                } catch (SAXParseException saxException) {
                    System.out.println(finalString);
                    System.out.println(saxException);
                }
                stringBuilder = new StringBuilder();
            }

            //
//                System.out.println(stringBuilder);
//                saxParser.parse(data, saxHandler);
//
//                List<Weatherstation> weatherstations = saxHandler.getWeatherstations();
//                for(Weatherstation weatherstation : weatherstations)
//                {
//                    System.out.println("Weatherstation Id = " + weatherstation.getStn());
//                }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Method for adding data to the Inputstream variable: data
     */
    public void addData(InputStream input) {
        this.data = input;
    }

    public void stopRunning()
    {
        running = false;
    }




}
