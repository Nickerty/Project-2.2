

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

    /**
     * Constructor for the XMLReader class
     *
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
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            SAXHandler saxHandler = new SAXHandler(mergeData);
            while (true) {
                String line;
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
                    System.out.println("Application was not able to read the incoming file: "+NE);
                }
                String finalString = stringBuilder.toString();
                InputSource saxInputSource = new InputSource(new StringReader(finalString));
                try {
                    saxParser.parse(saxInputSource, saxHandler);
                } catch (SAXParseException saxException) {
                    System.out.println("Parsing failed: "+saxException);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method for overwriting the data field with the given InputStream variable
     *
     * @param input an InputStream which will be used to overwrite the data field
     */
    public void addData(InputStream input) {
        this.data = input;
    }
}
