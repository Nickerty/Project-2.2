package XMLParsing;

import java.io.*;
import java.util.List;

import javax.xml.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.sql.SQLXML;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class XMLReader implements Runnable {
    private InputStream data;



    @Override
    public void run() {
        try
        {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            XMLParsing.SAXHandler saxHandler = new XMLParsing.SAXHandler();

            while(true) {
                StringBuilder stringBuilder = new StringBuilder();
                String rowLines = null;
                String line = null;

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(data, "UTF-8"));
                line = bufferedReader.readLine();
                while (!line.equals("</WEATHERDATA>")) {
                    line = bufferedReader.readLine();
                    stringBuilder.append(line);
                }
                InputSource saxInputSource = new InputSource(new StringReader(stringBuilder.toString()));
                saxParser.parse(saxInputSource, saxHandler);
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
    public void addData(InputStream input) {
        this.data = input;
    }




}
