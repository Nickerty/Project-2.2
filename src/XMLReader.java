

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
            StringBuilder stringBuilder = new StringBuilder();

            while(true) {
                String rowLines = null;
                String line = null;

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(data, "UTF-8"));
                line = bufferedReader.readLine();
                stringBuilder.append(line);
                while (!line.equals("</WEATHERDATA>")) {
                    line = bufferedReader.readLine();
                    stringBuilder.append(line);
                }
                InputSource saxInputSource = new InputSource(new StringReader(stringBuilder.toString()));
                saxParser.parse(saxInputSource, saxHandler);
                stringBuilder = new StringBuilder();

                TimeUnit.MILLISECONDS.sleep(900);
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




}
